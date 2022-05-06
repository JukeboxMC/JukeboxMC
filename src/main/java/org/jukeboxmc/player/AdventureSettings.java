package org.jukeboxmc.player;

import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.data.PlayerPermission;
import com.nukkitx.protocol.bedrock.data.command.CommandPermission;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jukeboxmc.util.NonStream;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author KCodeYT
 * @version 1.0
 */
public class AdventureSettings {

    private final Map<Type, Boolean> values = new EnumMap<>( Type.class );
    private final Player player;

    public AdventureSettings( Player player ) {
        this.player = player;
    }

    public AdventureSettings set( Type type, boolean value ) {
        this.values.put( type, value );
        return this;
    }

    public boolean get( Type type ) {
        final Boolean value = this.values.get( type );
        return value == null ? type.getDefaultValue() : value;
    }

    public void update() {
        AdventureSettingsPacket adventureSettingsPacket = new AdventureSettingsPacket();
        adventureSettingsPacket.setUniqueEntityId( this.player.getEntityId() );
        adventureSettingsPacket.setCommandPermission( CommandPermission.OPERATOR );
        adventureSettingsPacket.setPlayerPermission( PlayerPermission.OPERATOR );
        adventureSettingsPacket.getSettings().addAll( NonStream.filterAndMap( Type.values(), this::get, Type::getFlag, HashSet::new ) );
        this.player.sendPacket( adventureSettingsPacket );
    }


    @Getter
    @RequiredArgsConstructor
    public enum Type {
        WORLD_IMMUTABLE( AdventureSetting.WORLD_IMMUTABLE, false ),
        NO_PVM( AdventureSetting.NO_PVM, false ),
        NO_MVP( AdventureSetting.NO_MVP, false ),
        SHOW_NAME_TAGS( AdventureSetting.SHOW_NAME_TAGS, true ),
        AUTO_JUMP( AdventureSetting.AUTO_JUMP, true ),
        ALLOW_FLIGHT( AdventureSetting.MAY_FLY, false ),
        NO_CLIP( AdventureSetting.NO_CLIP, false ),
        WORLD_BUILDER( AdventureSetting.WORLD_BUILDER, true ),
        FLYING( AdventureSetting.FLYING, false ),
        MUTED( AdventureSetting.MUTED, false ),
        MINE( AdventureSetting.MINE, true ),
        DOORS_AND_SWITCHED( AdventureSetting.DOORS_AND_SWITCHES, true ),
        OPEN_CONTAINERS( AdventureSetting.OPEN_CONTAINERS, true ),
        ATTACK_PLAYERS( AdventureSetting.ATTACK_PLAYERS, true ),
        ATTACK_MOBS( AdventureSetting.ATTACK_MOBS, true ),
        OPERATOR( AdventureSetting.OPERATOR, false ),
        TELEPORT( AdventureSetting.TELEPORT, false ),
        BUILD( AdventureSetting.BUILD, true );

        private final AdventureSetting flag;
        private final Boolean defaultValue;
    }
}
