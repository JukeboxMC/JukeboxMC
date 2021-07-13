package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.type.CommandOrigin;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class CommandRequestPacket extends Packet {

    private String inputCommand;
    private CommandOrigin commandOrigin;

    @Override
    public int getPacketId() {
        return Protocol.COMMAND_REQUEST_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.inputCommand = this.readString();
        this.commandOrigin = this.readCommandOrigin();
    }
}