package org.jukeboxmc.entity;

import org.jukeboxmc.entity.metadata.Metadata;
import org.jukeboxmc.entity.metadata.MetadataFlag;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class Entity {

    private long entityId = 0;
    private long entityCount = 1;
    private Metadata metadata;

    public Entity() {
        this.metadata = new Metadata();
        this.metadata.setLong( MetadataFlag.INDEX, 0 );
        this.metadata.setShort( MetadataFlag.MAX_AIR, (short) 400 );
        this.metadata.setFloat( MetadataFlag.SCALE, 1 );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_WIDTH, 0.6f );
        this.metadata.setFloat( MetadataFlag.BOUNDINGBOX_HEIGHT, 1.8f );
        this.metadata.setShort( MetadataFlag.AIR, (short) 0 );
        this.metadata.setDataFlag( MetadataFlag.INDEX, MetadataFlag.AFFECTED_BY_GRAVITY, true );
    }

    public abstract String getEntityType();

    public long getAndIncrementEntityCount() {
        return this.entityCount++;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId( long entityId ) {
        this.entityId = entityId;
    }
}
