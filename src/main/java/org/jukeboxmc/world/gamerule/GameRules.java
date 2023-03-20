package org.jukeboxmc.world.gamerule;

import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.packet.GameRulesChangedPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GameRules {

    private final Map<GameRule, Object> gameRules = new HashMap<>();

    private @Nullable GameRulesChangedPacket changedPacket;

    public GameRules() {
        for ( GameRule gameRule : GameRule.values() ) {
            this.gameRules.put(gameRule, gameRule.getDefaultValue());
        }
    }

    public @NotNull List<GameRuleData<?>> getGameRules() {
        final Set<Map.Entry<GameRule, Object>> entrySet = this.gameRules.entrySet();
        final List<GameRuleData<?>> networkList = new ArrayList<>( entrySet.size() );
        for ( Map.Entry<GameRule, Object> entry : entrySet ) {
            networkList.add( new GameRuleData<>( entry.getKey().getIdentifier(), entry.getValue() ) );
        }
        return networkList;
    }

    public void set( GameRule gameRule, Object o ) {
        this.gameRules.put( gameRule, o );
        this.changedPacket = null;
    }

    public <V> V get(@NotNull GameRule gameRule ) {
        return (V) this.gameRules.getOrDefault( gameRule, gameRule.getDefaultValue() );
    }

    public boolean requiresUpdate() {
        return this.changedPacket == null;
    }

    public GameRulesChangedPacket updatePacket() {
        if ( !this.requiresUpdate() ) {
            return this.changedPacket;
        }
        this.changedPacket = new GameRulesChangedPacket();
        this.changedPacket.getGameRules().addAll( this.getGameRules() );
        return this.changedPacket;
    }

}
