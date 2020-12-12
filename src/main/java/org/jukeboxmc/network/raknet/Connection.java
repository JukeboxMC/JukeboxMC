package org.jukeboxmc.network.raknet;

import com.google.common.primitives.UnsignedInteger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.Getter;
import org.jukeboxmc.network.protocol.Protocol;
import org.jukeboxmc.network.protocol.packet.*;
import org.jukeboxmc.network.raknet.protocol.*;
import org.jukeboxmc.network.raknet.protocol.Packet;
import org.jukeboxmc.network.raknet.utils.BinaryStream;
import org.jukeboxmc.network.raknet.utils.Zlib;
import org.jukeboxmc.player.PlayerConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.DataFormatException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Connection {

    private Listener listener;
    private short mtuSize;
    @Getter
    private InetSocketAddress sender;

    private Status state = Status.CONNECTING;

    private LinkedList<Integer> nackQueue = new LinkedList<>();
    private LinkedList<Integer> ackQueue = new LinkedList<>();

    private ConcurrentHashMap<Integer, DataPacket> recoveryQueue = new ConcurrentHashMap<>();

    private LinkedList<DataPacket> packetToSend = new LinkedList<>();

    // private DataPacket sendQueue = new DataPacket();
    private Queue<DataPacket> sendQueue = new ConcurrentLinkedQueue<>();

    private ConcurrentHashMap<Integer, Map<Integer, EncapsulatedPacket>> splitPackets = new ConcurrentHashMap<>();

    private int windowStart = -1;
    private int windowEnd = 2048;
    private int reliableWindowStart = 0;
    private int reliableWindowEnd = 2048;
    private ConcurrentHashMap<Integer, EncapsulatedPacket> reliableWindow = new ConcurrentHashMap<>();
    private int lastReliableIndex = -1;

    private Queue<Integer> receivedWindow = new ConcurrentLinkedQueue<>();

    private int lastSequenceNumber = -1;
    private int sendSequenceNumber = 0;

    private int messageIndex;
    private ConcurrentHashMap<Integer, Integer> channelIndex = new ConcurrentHashMap<>();

    private int splitID = 0;

    private long lastUpdate;
    private boolean isActive = false;

    private final Queue<org.jukeboxmc.network.protocol.packet.Packet> packets = new ConcurrentLinkedQueue<>();

    public Connection( Listener listener, short mtuSize, InetSocketAddress sender ) {
        this.listener = listener;
        this.mtuSize = mtuSize;
        this.sender = sender;

        this.lastUpdate = System.currentTimeMillis();

        for ( int i = 0; i < 32; i++ ) {
            this.channelIndex.put( i, 0 );
        }
    }

    public void update( long timestap ) {
        if ( !this.isActive && this.lastUpdate + 5000 < timestap ) {
            this.disconnect( "timeout" );
            return;
        }
        this.isActive = false;

        if ( this.ackQueue.size() > 0 ) {
            ACK packet = new ACK();
            packet.setPackets( this.ackQueue );
            this.sendPacket( packet );
            this.ackQueue.clear();
        }

        if ( this.nackQueue.size() > 0 ) {
            NACK packet = new NACK();
            packet.setPackets( this.nackQueue );
            this.sendPacket( packet );
            this.nackQueue.clear();
        }

        if ( this.packetToSend.size() > 0 ) {
            int limit = 16;
            for ( DataPacket dataPacket : this.packetToSend ) {
                dataPacket.sendTime = timestap;
                dataPacket.write();
                this.recoveryQueue.put( dataPacket.sequenceNumber, dataPacket );
                this.packetToSend.remove( dataPacket );
                this.sendPacket( dataPacket );

                if ( --limit <= 0 ) {
                    break;
                }
            }

            if ( this.packetToSend.size() > 2048 ) {
                this.packetToSend.clear();
            }
        }

        this.recoveryQueue.forEach( ( seq, packet ) -> {
            if ( packet.sendTime < System.currentTimeMillis() - 8000 ) {
                this.packetToSend.add( packet );
                this.recoveryQueue.remove( seq );
            }
        } );

        for ( Integer seq : this.receivedWindow ) {
            if ( seq < this.windowStart ) {
                this.receivedWindow.remove( seq );
            } else {
                break;
            }
        }
        this.sendQueue();
    }

    public void receive( ByteBuf buffer ) {
        this.isActive = true;
        this.lastUpdate = System.currentTimeMillis();

        int packetId = buffer.getUnsignedByte( 0 );

        if ( ( packetId & BitFlags.VALID ) == 0 ) {
            //Ignore
        } else if ( ( packetId & BitFlags.ACK ) != 0 ) {
            this.handleAck( buffer );
        } else if ( ( packetId & BitFlags.NACK ) != 0 ) {
            this.handleNack( buffer );
        } else {
            ByteBuf duplicate = buffer.duplicate();
            byte[] array = new byte[duplicate.readableBytes()];
            duplicate.readBytes( array );
            //System.out.println( Arrays.toString( array ) );
            //System.out.println( "Datagram [" + packetId + "] -> " + Arrays.toString( array ) );
            this.handleDatagram( buffer );
        }
    }

    private void handleDatagram( ByteBuf buffer ) {
        DataPacket dataPacket = new DataPacket();
        dataPacket.buffer = buffer;
        dataPacket.read();

        if ( dataPacket.sequenceNumber < this.windowStart || dataPacket.sequenceNumber > this.windowEnd || this.receivedWindow.contains( dataPacket.sequenceNumber ) ) {
            return;
        }

        int diff = dataPacket.sequenceNumber - this.lastSequenceNumber;

        this.nackQueue.remove( (Integer) dataPacket.sequenceNumber );
        this.ackQueue.add( dataPacket.sequenceNumber );
        this.receivedWindow.add( dataPacket.sequenceNumber );

        if ( diff != 1 ) {
            for ( int i = dataPacket.sequenceNumber + 1; i < dataPacket.sequenceNumber; i++ ) {
                if ( !this.receivedWindow.contains( i ) ) {
                    this.nackQueue.add( i );
                }
            }
        }

        if ( diff >= 1 ) {
            this.lastSequenceNumber = dataPacket.sequenceNumber;
            this.windowStart += diff;
            this.windowEnd += diff;
        }

        for ( Object packet : dataPacket.getPackets() ) {
            if ( packet instanceof EncapsulatedPacket ) {
                this.receivePacket( (EncapsulatedPacket) packet );
            }
        }
    }

    private void receivePacket( EncapsulatedPacket packet ) {
        if ( packet.messageIndex == -1 ) {
            this.handlePacket( packet );
        } else {
            if ( packet.messageIndex < this.reliableWindowStart || packet.messageIndex > this.reliableWindowEnd ) {
                return;
            }

            if ( packet.messageIndex - this.lastReliableIndex == 1 ) {
                this.lastReliableIndex++;
                this.reliableWindowStart++;
                this.reliableWindowEnd++;
                this.handlePacket( packet );

                if ( this.reliableWindow.size() > 0 ) {
                    TreeMap<Integer, EncapsulatedPacket> sortedMap = new TreeMap<>( this.reliableWindow );

                    for ( int index : sortedMap.keySet() ) {
                        EncapsulatedPacket pk = this.reliableWindow.get( index );

                        if ( ( index - this.lastReliableIndex ) != 1 ) {
                            break;
                        }

                        this.lastReliableIndex++;
                        this.reliableWindowStart++;
                        this.reliableWindowEnd++;
                        this.handlePacket( pk );
                        this.reliableWindow.remove( index );
                    }
                }
            } else {
                this.reliableWindow.put( packet.messageIndex, packet );
            }
        }
    }

    private void handlePacket( EncapsulatedPacket packet ) {
        if ( packet.split ) {
            if ( this.state == Status.CONNECTED ) {
                this.handleSplit( packet );
            }
            return;
        }
        int id = packet.getBuffer().getUnsignedByte( 0 );

        if ( id < 0x80 ) {
            if ( this.state == Status.CONNECTING ) {
                if ( id == Protocol.CONNECTION_REQUEST ) {
                    this.handleConnectionRequest( packet.getBuffer() );
                } else if ( id == Protocol.NEW_INCOMING_CONNECTION ) {
                    NewIncomingConnection dataPacket = new NewIncomingConnection();
                    dataPacket.buffer = packet.getBuffer();
                    dataPacket.read();

                    if ( dataPacket.getAddress().getPort() == this.listener.getAddress().getPort() ) {
                        this.state = Status.CONNECTED;
                    }
                }
            } else if ( id == Protocol.DISCONNECT_NOTIFICATION ) {
                this.disconnect( "Client disconnect" );
            } else if ( id == Protocol.CONNECTED_PING ) {
                this.handleConnectedPing( packet.getBuffer() ); //HERE nach dem Senden eines Packets
            }
        } else if ( this.state == Status.CONNECTED ) {
            ByteBuf buffer = packet.getBuffer();
            if ( id == Protocol.BATCH_PACKET ) {
                buffer.readerIndex( 1 );
                BatchPacket batchPacket = new BatchPacket();
                batchPacket.setBuffer( buffer );
                batchPacket.read();
                this.packets.offer( batchPacket );
            } else {
                System.out.println( "ElseID: " + id );
            }

            org.jukeboxmc.network.protocol.packet.Packet dataPacket;
            while ( ( dataPacket = this.packets.poll() ) != null ) {
                if ( dataPacket instanceof BatchPacket ) {
                    this.processBatch( (BatchPacket) dataPacket );
                }
            }
        }
    }

    private BinaryStream getPacketBinaryStream( ByteBuf byteBuf ) {
        int packetLength = new BinaryStream( byteBuf ).readUnsignedVarInt();
        return new BinaryStream( byteBuf.readBytes( packetLength ) );
    }

    //Step 2
    public void sendPacket( org.jukeboxmc.network.protocol.packet.Packet packet ) {
        BatchPacket batchPacket = new BatchPacket();
        batchPacket.addPacket( packet );
        batchPacket.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = batchPacket.getBuffer();

        this.addEncapsulatedToQueue( encapsulatedPacket, Priority.NORMAL );
    }

    private void processBatch( BatchPacket packet ) {
        byte[] data;
        try {
            byte[] payload = packet.payload;
            data = Zlib.infalte( payload );
        } catch ( DataFormatException | IOException e ) {
            e.printStackTrace();
            return;
        }

        ByteBuf buffer = Unpooled.wrappedBuffer( data );

        while ( buffer.readableBytes() > 0 ) {
            BinaryStream binaryStream = this.getPacketBinaryStream( buffer );
            int packetId = binaryStream.readUnsignedVarInt();

            System.out.println( "PacketID: " + packetId );

            if ( packetId == 0x01 ) {
                System.out.println( "LoginPacket" );
                LoginPacket loginPacket = new LoginPacket();
                loginPacket.setBuffer( binaryStream.getBuffer() );
                loginPacket.read();

                PlayStatusPacket playStatusPacket = new PlayStatusPacket();
                playStatusPacket.setStatus( PlayStatusPacket.Status.LOGIN_SUCCESS );
                this.sendPacket( playStatusPacket );

                ResourcePacksInfoPacket resourcePacksInfoPacket = new ResourcePacksInfoPacket();
                resourcePacksInfoPacket.setScripting( false );
                resourcePacksInfoPacket.setForceAccept( false );
                this.sendPacket( resourcePacksInfoPacket );
            } else if ( packetId == 0x08 ) {
                ResourcePackResponsePacket resourcePackResponsePacket = new ResourcePackResponsePacket();
                resourcePackResponsePacket.setBuffer( binaryStream.getBuffer() );
                resourcePackResponsePacket.read();
            }
        }
    }

    private void handleSplit( EncapsulatedPacket packet ) {
        if ( !this.splitPackets.containsKey( packet.splitID ) ) {
            this.splitPackets.put( packet.splitID, new HashMap<Integer, EncapsulatedPacket>() {{
                this.put( packet.splitIndex, packet );
            }} );
        } else {
            this.splitPackets.get( packet.splitID ).put( (int) packet.splitIndex, packet );
        }

        if ( this.splitPackets.get( packet.splitID ).size() == packet.splitCount ) {
            EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();

            BinaryStream stream = new BinaryStream();
            for ( int i = 0; i < packet.splitCount; i++ ) {
                stream.writeBuffer( this.splitPackets.get( packet.splitID ).get( i ).getBuffer() );
            }
            encapsulatedPacket.setBuffer( stream.getBuffer() );
            this.splitPackets.remove( packet.splitID );

            this.handlePacket( encapsulatedPacket );
        }

        /*
                if ( this.splitPackets.containsKey( packet.splitID ) ) {
            Map<Integer, EncapsulatedPacket> value = this.splitPackets.get( packet.splitID );
            value.put( packet.splitIndex, packet );
            this.splitPackets.put( packet.splitID, value );
        } else {
            Map<Integer, EncapsulatedPacket> value = new HashMap<>();
            value.put( packet.splitID, packet );
            this.splitPackets.put( packet.splitIndex, value );
        }

        Map<Integer, EncapsulatedPacket> localSplits = this.splitPackets.get( packet.splitID );
        if ( localSplits.size() == packet.splitCount ) {
            EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
            ByteBuf stream = Unpooled.buffer( 0 );
            for ( EncapsulatedPacket packets : localSplits.values() ) {
                stream.writeBytes( packets.getBuffer() );
            }
            this.splitPackets.remove( packet.splitID );

            encapsulatedPacket.buffer = stream;
            this.receivePacket( encapsulatedPacket );
        }

         */
    }

    private void handleConnectionRequest( ByteBuf buffer ) {
        ConnectionRequest dataPacket = new ConnectionRequest();
        dataPacket.buffer = buffer;
        dataPacket.read();

        ConnectionRequestAccepted packet = new ConnectionRequestAccepted();
        packet.setAddress( this.sender );
        packet.setRequestTimestamp( dataPacket.getRequestTimestamp() );
        packet.setAcceptedTimestamp( System.currentTimeMillis() );
        packet.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = packet.buffer;
        this.addToQueue( encapsulatedPacket, Priority.IMMEDIATE );
    }

    private void handleConnectedPing( ByteBuf buffer ) {
        ConnectedPing dataPacket = new ConnectedPing();
        dataPacket.buffer = buffer;
        dataPacket.read();

        ConnectedPong packet = new ConnectedPong();
        packet.setClientTimestamp( dataPacket.getClientTimestamp() );
        packet.setServerTimestamp( System.currentTimeMillis() );
        packet.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = packet.buffer;

        this.addToQueue( encapsulatedPacket, Priority.NORMAL );
        System.out.println( "ping" );
    }

    private void handleAck( ByteBuf buffer ) {
        ACK packet = new ACK();
        packet.buffer = buffer;
        packet.read();

        for ( Integer seq : packet.getPackets() ) {
            this.recoveryQueue.remove( seq );
        }
    }

    private void handleNack( ByteBuf buffer ) {
        NACK packet = new NACK();
        packet.buffer = buffer;
        packet.read();

        for ( Integer seq : packet.getPackets() ) {
            if ( this.recoveryQueue.containsKey( seq ) ) {
                DataPacket dataPacket = this.recoveryQueue.get( seq );
                dataPacket.setSequenceNumber( this.sendSequenceNumber++ );
                dataPacket.setSendTime( System.currentTimeMillis() );
                dataPacket.write();
                this.sendPacket( dataPacket );

                this.recoveryQueue.remove( seq );
            }
        }
    }

    public void disconnect( String timeout ) {
        this.listener.removeConnection( this, timeout );
    }

    //Step 3
    public void addEncapsulatedToQueue( EncapsulatedPacket packet, int flags ) {
        if ( packet.reliability == 2 || packet.reliability == 3 || packet.reliability == 4 || packet.reliability == 6 || packet.reliability == 7 ) {
            packet.messageIndex = this.messageIndex++;
            if ( packet.reliability == 3 ) {
                int index = this.channelIndex.get( packet.orderChannel ) + 1;
                packet.orderIndex = index;
                this.channelIndex.put( packet.orderChannel, index );
            }
        }

        if ( packet.getTotalLength() + 4 > this.mtuSize ) {
            Map<Integer, ByteBuf> buffers = new HashMap<>();
            int i = 0;
            int splitIndex = 0;

            while ( i < packet.buffer.capacity() ) {
                buffers.put( ( splitIndex += 1 ) - 1, packet.buffer.slice( i, ( i += this.mtuSize - 60 ) ) );
            }

            int splitID = ++this.splitID % 65536;
            buffers.forEach( ( count, buffer ) -> {
                System.out.println( "TRY SEND ENCAPSULATED PACKET" );
                EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
                encapsulatedPacket.splitID = splitID;
                encapsulatedPacket.split = true;
                encapsulatedPacket.splitCount = buffers.size();
                encapsulatedPacket.reliability = packet.reliability;
                encapsulatedPacket.splitIndex = count;
                encapsulatedPacket.buffer = buffer;
                if ( count > 0 ) {
                    encapsulatedPacket.messageIndex = this.messageIndex++;
                } else {
                    encapsulatedPacket.messageIndex = packet.messageIndex;
                }
                if ( encapsulatedPacket.reliability == 3 ) {
                    encapsulatedPacket.orderChannel = packet.orderChannel;
                    encapsulatedPacket.orderIndex = packet.orderIndex;
                }
                this.addToQueue( encapsulatedPacket, flags | Priority.IMMEDIATE );
            } );
        } else {
            this.addToQueue( packet, flags );
        }
    }

    //Step 4
    private void addToQueue( EncapsulatedPacket encapsulatedPacket, int priority ) {
        if ( priority == Priority.IMMEDIATE ) {
            DataPacket packet = new DataPacket();
            packet.sequenceNumber = this.sendSequenceNumber++;
            packet.getPackets().add( encapsulatedPacket.toBinary() );
            this.sendPacket( packet );
            packet.sendTime = System.currentTimeMillis();
            this.recoveryQueue.put( packet.sequenceNumber, packet );
            return;
        }

        for ( DataPacket dataPacket : this.sendQueue ) {
            int length = dataPacket.length();
            if ( length + encapsulatedPacket.getTotalLength() > this.mtuSize ) {
                this.sendQueue();
            }
        }

        DataPacket dataPacket = new DataPacket();
        dataPacket.getPackets().add( encapsulatedPacket.toBinary() );
        this.sendQueue.add( dataPacket );
        System.out.println( "ADD" );
    }

    private void sendQueue() {
        if ( !this.sendQueue.isEmpty() ) {
            DataPacket dataPacket;
            while ( ( dataPacket = this.sendQueue.poll() ) != null ) {
                dataPacket.setSequenceNumber( this.sendSequenceNumber++ );
                this.sendPacket( dataPacket );
                this.recoveryQueue.put( dataPacket.sequenceNumber, dataPacket );
                System.out.println( "Send Packet..." );
            }
        }

        /*
                if ( !this.sendQueue.getPackets().isEmpty() ) {
            this.sendQueue.sequenceNumber = this.sendSequenceNumber++;
            this.sendPacket( this.sendQueue );
            this.sendQueue.sendTime = System.currentTimeMillis();
            this.recoveryQueue.put( this.sendQueue.sequenceNumber, this.sendQueue );
            this.sendQueue = new DataPacket();
            System.out.println( "Send" );
        }
         */
    }

    public void sendPacket( Packet packet ) {
        packet.write();
        this.listener.sendBuffer( packet.buffer, this.sender );
    }

    public void close() {
        ByteBuf buffer = Unpooled.buffer( 1 );
        buffer.writeBytes( new byte[]{ 0x00, 0x00, 0x08, 0x15 } );
        EncapsulatedPacket packet = EncapsulatedPacket.fromBinary( buffer );
        this.addEncapsulatedToQueue( packet, Priority.IMMEDIATE );
    }


    public static class Priority {
        public static final int NORMAL = 0;
        public static final int IMMEDIATE = 1;
    }

    public enum Status {
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }
}
