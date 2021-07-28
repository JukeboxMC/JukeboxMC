package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.player.GameMode;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetGamemodePacket extends Packet {

    private GameMode gameMode;

    @Override
    public int getPacketId() {
        return Protocol.SET_GAMEMODE_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.gameMode.ordinal() );
    }
}
