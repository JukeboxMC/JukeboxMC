package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.world.Difficulty;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetDifficultyPacket extends Packet {

    private Difficulty difficulty;

    @Override
    public int getPacketId() {
        return Protocol.SET_DIFFICULTY_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( this.difficulty.ordinal() );
    }
}
