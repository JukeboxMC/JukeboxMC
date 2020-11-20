package org.jukeboxmc.network.raknet.protocol;

import org.jukeboxmc.network.protocol.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class ACK extends AcknowledgePacket {

    public ACK() {
        super( Protocol.ACKNOWLEDGE_PACKET );
    }
}
