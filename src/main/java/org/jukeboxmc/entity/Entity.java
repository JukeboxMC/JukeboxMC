package org.jukeboxmc.entity;

import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    public static long entityCount = 1;
    private long entityId = 0;
    private Metadata metadata;

    public Entity() {
        this.metadata = new Metadata();
        this.metadata.setLong( MetadataFlag.INDEX, 0 );
        this.metadata.setShort( MetadataFlag.MAX_AIR, (short) 400 );
        this.metadata.setFloat( MetadataFlag.SCALE, 1 );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_WIDTH, 0.6f );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_HEIGHT, 1.8f );
        this.metadata.setShort( MetadataFlag.AIR, (short) 0 );
        this.metadata.setDataFlag( MetadataFlag.INDEX, MetadataFlag.HAS_COLLISION, true );
        this.metadata.setDataFlag( MetadataFlag.INDEX, MetadataFlag.AFFECTED_BY_GRAVITY, true );
    }

    public abstract String getEntityType();

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId( long entityId ) {
        this.entityId = entityId;
    }

    public void setNameTag( String value ) {
        this.metadata.setString( MetadataFlag.NAMETAG, value );
    }

    public String getNameTag() {
        return this.metadata.getString( MetadataFlag.NAMETAG );
    }

    public void setNameTagVisible( boolean value ) {
        this.metadata.setDataFlag( MetadataFlag.INDEX, MetadataFlag.SHOW_NAMETAG, value );
    }

    public boolean isNameTagVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, MetadataFlag.SHOW_NAMETAG );
    }

    public void setNameTagAlwaysVisible( boolean value ) {
        this.metadata.setDataFlag( MetadataFlag.INDEX, MetadataFlag.SHOW_ALWAYS_NAMETAG, value );
    }

    public boolean isNameTagAlwaysVisible() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, MetadataFlag.SHOW_ALWAYS_NAMETAG );
    }
 }
