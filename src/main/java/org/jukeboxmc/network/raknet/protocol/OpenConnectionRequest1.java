package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.network.protocol.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
@Setter
public class OpenConnectionRequest1 extends Packet {

    private byte protocol = 10;
    private short mtu;

    public OpenConnectionRequest1() {
        super( Protocol.OPEN_CONNECTION_REQUEST_1 );
    }

    @Override
    public void read() {
        super.read();
        this.readMagic();
        this.protocol = this.buffer.readByte();
        this.mtu = (short) this.consumePadding();
    }

}
