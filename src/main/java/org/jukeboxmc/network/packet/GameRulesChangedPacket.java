package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.world.GameRule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GameRulesChangedPacket extends Packet {

    private Map<GameRule<?>, Object> gamerules = new HashMap<>();

    @Override
    public int getPacketId() {
        return Protocol.GAME_RULES_CHANGED_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeGameRules( this.gamerules );
    }
}
