package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author Kaooot
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetCommandsEnabledPacket extends Packet {

    private boolean enabled;

    @Override
    public int getPacketId() {
        return Protocol.SET_COMMANDS_ENABLED_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBoolean( this.enabled );
    }
}