package org.jukeboxmc.entity.metadata;

import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Metadata {

    private final EntityDataMap entityDataMap = new EntityDataMap();

    public @NotNull Metadata setByte(EntityData entityData, byte value ) {
        Byte oldValue = this.getByte( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putByte( entityData, value );
        }
        return this;
    }

    public @NotNull Byte getByte(EntityData entityData ) {
        return this.entityDataMap.getByte( entityData );
    }

    public @NotNull Metadata setLong(EntityData entityData, long value ) {
        Long oldValue = this.getLong( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putLong( entityData, value );
        }
        return this;
    }

    public @NotNull Long getLong(EntityData entityData ) {
        return this.entityDataMap.getLong( entityData );
    }

    public @NotNull Metadata setShort(EntityData entityData, short value ) {
        Short oldValue = this.getShort( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putShort( entityData, value );
        }
        return this;
    }

    public @NotNull Short getShort(EntityData entityData ) {
        return this.entityDataMap.getShort( entityData );
    }

    public @NotNull Metadata setString(EntityData entityData, String value ) {
        String oldValue = this.getString( entityData );
        if ( !Objects.equals( oldValue, value ) ) {
            this.entityDataMap.putString( entityData, value );
        }
        return this;
    }

    public String getString( EntityData entityData ) {
        return this.entityDataMap.getString( entityData );
    }

    public @NotNull Metadata setFloat(EntityData entityData, float value ) {
        Float oldValue = this.getFloat( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putFloat( entityData, value );
        }
        return this;
    }

    public @NotNull Float getFloat(EntityData entityData ) {
        return this.entityDataMap.getFloat( entityData );
    }

    public @NotNull Metadata setFlag(@NotNull EntityFlag entityFlag, boolean value ) {
        boolean oldValue = this.getFlag( entityFlag );
        if ( oldValue != value ) {
            this.entityDataMap.getOrCreateFlags().setFlag( entityFlag, value );
        }
        return this;
    }

    public @NotNull Integer getInt(EntityData entityData ) {
        return this.entityDataMap.getInt( entityData );
    }

    public @NotNull Metadata setInt(EntityData entityData, int value ) {
        Integer oldValue = this.getInt( entityData );

        if ( oldValue != value ) {
            this.entityDataMap.putInt( entityData, value );
        }

        return this;
    }

    public boolean getFlag(@NotNull EntityFlag entityFlag ) {
        return this.entityDataMap.getOrCreateFlags().getFlag( entityFlag );
    }

    public @NotNull EntityDataMap getEntityDataMap() {
        return this.entityDataMap;
    }
}
