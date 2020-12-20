package org.jukeboxmc.entity.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Metadata {

    private Map<MetadataFlag, MetadataValue> metadata = new HashMap<>();

    public void setByte( MetadataFlag metadataFlag, byte value ) {
        this.metadata.put( metadataFlag, new MetadataValue( MetadataValue.FlagType.BYTE, value ) );
    }

    public Byte getByte( MetadataFlag metadataFlag ) {
        MetadataValue metadataValue = this.metadata.get( metadataFlag );
        return metadataValue.getFlagType() == MetadataValue.FlagType.BYTE ? (Byte) metadataValue.getValue() : null;
    }

    public void setLong( MetadataFlag metadataFlag, long value ) {
        this.metadata.put( metadataFlag, new MetadataValue( MetadataValue.FlagType.LONG, value ) );
    }

    public Long getLong( MetadataFlag metadataFlag ) {
        MetadataValue metadataValue = this.getMetadataValue( metadataFlag );
        return metadataValue.getFlagType() == MetadataValue.FlagType.LONG ? (Long) metadataValue.getValue() : null;
    }

    public void setShort( MetadataFlag metadataFlag, short value ) {
        this.metadata.put( metadataFlag, new MetadataValue( MetadataValue.FlagType.SHORT, value ) );
    }

    public Short getShort( MetadataFlag metadataFlag ) {
        MetadataValue metadataValue = this.metadata.get( metadataFlag );
        return metadataValue.getFlagType() == MetadataValue.FlagType.SHORT ? (Short) metadataValue.getValue() : null;
    }

    public void setString( MetadataFlag metadataFlag, String value ) {
        this.metadata.put( metadataFlag, new MetadataValue( MetadataValue.FlagType.STRING, value ) );
    }

    public String getString( MetadataFlag metadataFlag ) {
        MetadataValue metadataValue = this.metadata.get( metadataFlag );
        return metadataValue.getFlagType() == MetadataValue.FlagType.STRING ? (String) metadataValue.getValue() : null;
    }

    public void setFloat( MetadataFlag metadataFlag, float value ) {
        this.metadata.put( metadataFlag, new MetadataValue( MetadataValue.FlagType.FLOAT, value ) );
    }

    public Float getFloat( MetadataFlag metadataFlag ) {
        MetadataValue metadataValue = this.metadata.get( metadataFlag );
        return metadataValue.getFlagType() == MetadataValue.FlagType.FLOAT ? (Float) metadataValue.getValue() : null;
    }


    public MetadataValue getMetadataValue( MetadataFlag metadataFlag ) {
        return this.metadata.getOrDefault( metadataFlag, null );
    }

    public boolean getDataFlag( MetadataFlag firstFlag, MetadataFlag secondFlag ) {
          return ( firstFlag == MetadataFlag.PLAYER_FLAGS ? this.getByte( firstFlag ) & 0xFF : this.getLong( firstFlag ) & ( 1L << secondFlag.getId() ) ) > 0;
    }

    public void setDataFlag( MetadataFlag firstFlag, MetadataFlag secondFlag, boolean value ) {
        boolean dataFlag = this.getDataFlag( firstFlag, secondFlag );
        if ( dataFlag != value ) {
            if ( firstFlag == MetadataFlag.PLAYER_FLAGS  ) {
                byte flags = this.getByte( firstFlag );
                flags ^= 1 << secondFlag.getId();
                this.setByte( firstFlag, flags );
            } else {
                long flags = this.getLong( firstFlag );
                flags ^= 1L << secondFlag.getId();
                this.setLong( firstFlag, flags );
            }
        }
    }

    public Map<MetadataFlag, MetadataValue> getMetadata() {
        return this.metadata;
    }
}
