package org.jukeboxmc.network.raknet.protocol;

import org.jukeboxmc.network.protocol.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class NACK extends AcknowledgePacket {

    public NACK() {
        super( Protocol.NACKNOWLEDGE_PACKET );
    }
}
