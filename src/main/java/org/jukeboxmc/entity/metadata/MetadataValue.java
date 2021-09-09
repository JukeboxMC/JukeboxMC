package org.jukeboxmc.entity.metadata;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class MetadataValue {

    private final FlagType flagType;
    private final Object value;

    public MetadataValue( FlagType flagType, Object value ) {
        this.flagType = flagType;
        this.value = value;
    }

    public FlagType getFlagType() {
        return this.flagType;
    }

    public Object getValue() {
        return this.value;
    }

    public enum FlagType {

        BYTE,
        SHORT,
        INT,
        FLOAT,
        STRING,
        NBT,
        POSITION,
        LONG,
        VECTOR

    }

}
