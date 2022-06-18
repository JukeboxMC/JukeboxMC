package org.jukeboxmc.entity.metadata;

import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;

import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Metadata {

    private final EntityDataMap entityDataMap = new EntityDataMap();

    public Metadata setByte( EntityData entityData, byte value ) {
        Byte oldValue = this.getByte( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putByte( entityData, value );
        }
        return this;
    }

    public Byte getByte( EntityData entityData ) {
        return this.entityDataMap.getByte( entityData );
    }

    public Metadata setLong( EntityData entityData, long value ) {
        Long oldValue = this.getLong( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putLong( entityData, value );
        }
        return this;
    }

    public Long getLong( EntityData entityData ) {
        return this.entityDataMap.getLong( entityData );
    }

    public Metadata setShort( EntityData entityData, short value ) {
        Short oldValue = this.getShort( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putShort( entityData, value );
        }
        return this;
    }

    public Short getShort( EntityData entityData ) {
        return this.entityDataMap.getShort( entityData );
    }

    public Metadata setString( EntityData entityData, String value ) {
        String oldValue = this.getString( entityData );
        if ( !Objects.equals( oldValue, value ) ) {
            this.entityDataMap.putString( entityData, value );
        }
        return this;
    }

    public String getString( EntityData entityData ) {
        return this.entityDataMap.getString( entityData );
    }

    public Metadata setFloat( EntityData entityData, float value ) {
        Float oldValue = this.getFloat( entityData );
        if ( oldValue != value ) {
            this.entityDataMap.putFloat( entityData, value );
        }
        return this;
    }

    public Float getFloat( EntityData entityData ) {
        return this.entityDataMap.getFloat( entityData );
    }

    public Metadata setFlag( EntityFlag entityFlag, boolean value ) {
        boolean oldValue = this.getFlag( entityFlag );
        if ( oldValue != value ) {
            this.entityDataMap.getOrCreateFlags().setFlag( entityFlag, value );
        }
        return this;
    }

    public Integer getInt( EntityData entityData ) {
        return this.entityDataMap.getInt( entityData );
    }

    public Metadata setInt( EntityData entityData, int value ) {
        Integer oldValue = this.getInt( entityData );

        if ( oldValue != value ) {
            this.entityDataMap.putInt( entityData, value );
        }

        return this;
    }

    public boolean getFlag( EntityFlag entityFlag ) {
        return this.entityDataMap.getOrCreateFlags().getFlag( entityFlag );
    }

    public EntityDataMap getEntityDataMap() {
        return this.entityDataMap;
    }
}
