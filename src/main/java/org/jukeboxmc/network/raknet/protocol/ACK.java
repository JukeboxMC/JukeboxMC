package org.jukeboxmc.network.raknet.protocol;

import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class ACK extends AcknowledgeRakNetPacket {

    public ACK() {
        super( Protocol.ACKNOWLEDGE_PACKET );
    }
}
