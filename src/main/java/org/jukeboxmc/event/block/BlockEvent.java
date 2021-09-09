package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Event;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class BlockEvent extends Event {

    private Block block;

    /**
     * Creates a new {@link BlockEvent}
     *
     * @param block which represents the block which comes with this event
     */
    public BlockEvent( Block block ) {
        this.block = block;
    }

    /**
     * Retrives the {@link Block} which comes with this {@link BlockEvent}
     *
     * @return a fresh {@link Block}
     */
    public Block getBlock() {
        return this.block;
    }
}