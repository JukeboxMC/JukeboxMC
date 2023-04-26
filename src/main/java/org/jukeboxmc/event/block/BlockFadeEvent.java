package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;

public class BlockFadeEvent extends BlockEvent implements Cancellable {

    private final Block newState;

    /**
     * Creates a new {@link BlockEvent}
     *
     * @param block which represents the block which comes with this event
     */
    public BlockFadeEvent(Block block, Block newState) {
        super(block);
        this.newState = newState;
    }

    public Block getNewState() {
        return this.newState;
    }
}
