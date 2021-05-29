package org.jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class AcknowledgeRakNetPacket extends RakNetPacket {

    private List<Integer> packets = new CopyOnWriteArrayList<>();

    public AcknowledgeRakNetPacket( byte packetId ) {
        super( packetId );
    }

    @Override
    public void read() {
        super.read();
        this.packets.clear();

        short recordCount = this.buffer.readShort();

        for ( int i = 0; i < recordCount; i++ ) {
            int recordType = this.buffer.readByte();

            if ( recordType == 0 ) {
                int start = this.buffer.readMediumLE();
                int end = this.buffer.readMediumLE();

                for ( int pack = start; pack <= end; pack++ ) {
                    this.packets.add( pack );
                    if ( this.packets.size() > 4096 ) {
                        throw new IndexOutOfBoundsException( "Maximum acknowledgement packets size exceeded" );
                    }
                }
            } else {
                int packet = this.buffer.readMediumLE();
                this.packets.add( packet );
            }
        }
    }

    @Override
    public void write() {
        super.write();

        short records = 0;
        ByteBuf stream = Unpooled.buffer( 0 );
        //this.packets.sort( Collections.reverseOrder() );
        int count = this.packets.size();

        if ( count > 0 ) {
            int pointer = 1;
            int start = this.packets.get( 0 );
            int last = this.packets.get( 0 );

            while ( pointer < count ) {
                int current = this.packets.get( pointer++ );
                int diff = current - last;

                if ( diff == 1 ) {
                    last = current;
                } else if ( diff > 1 ) {
                    if ( start == last ) {
                        stream.writeBoolean( true );
                        stream.writeMediumLE( start );
                        start = last = current;
                    } else {
                        stream.writeBoolean( false );
                        stream.writeMediumLE( start );
                        stream.writeMediumLE( last );
                        start = last = current;
                    }
                    records++;
                }
            }

            if ( start == last ) {
                stream.writeBoolean( true );
                stream.writeMediumLE( start );
            } else {
                stream.writeBoolean( false );
                stream.writeMediumLE( start );
                stream.writeMediumLE( last );
            }
            records+=1;
        }
        this.buffer.writeShort( records );
        this.buffer.writeBytes( stream );
    }

}
