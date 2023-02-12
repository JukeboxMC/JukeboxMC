package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.entity.passiv.EntityFallingBlock;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FallingBlockEvent extends BlockEvent implements Cancellable {

    private final EntityFallingBlock entityFallingBlock;

    /**
     * Creates a new {@link BlockEvent}
     *
     * @param block which represents the block which comes with this event
     */
    public FallingBlockEvent( Block block, EntityFallingBlock entityFallingBlock ) {
        super( block );
        this.entityFallingBlock = entityFallingBlock;
    }

    public EntityFallingBlock getEntityFallingBlock() {
        return this.entityFallingBlock;
    }
}
