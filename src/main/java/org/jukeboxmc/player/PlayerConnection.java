package org.jukeboxmc.player;

import org.jukeboxmc.network.packet.BatchPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.protocol.EncapsulatedPacket;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerConnection {

    private Connection connection;

    public PlayerConnection( Connection connection ) {
        this.connection = connection;
    }

    public void sendPacket( Packet packet ) {
        BatchPacket batchPacket = new BatchPacket();
        batchPacket.addPacket( packet );
        batchPacket.write();

        EncapsulatedPacket encapsulatedPacket = new EncapsulatedPacket();
        encapsulatedPacket.reliability = 0;
        encapsulatedPacket.buffer = batchPacket.getBuffer();

        this.connection.addEncapsulatedToQueue( encapsulatedPacket, Connection.Priority.NORMAL );
    }

}
