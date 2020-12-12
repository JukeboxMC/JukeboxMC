package org.jukeboxmc.network.raknet.protocol;

import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class NACK extends AcknowledgeRakNetPacket {

    public NACK() {
        super( Protocol.NACKNOWLEDGE_PACKET );
    }
}
