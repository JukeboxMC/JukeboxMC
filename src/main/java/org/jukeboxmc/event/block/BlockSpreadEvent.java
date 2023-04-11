package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpreadEvent extends BlockEvent implements Cancellable {

    private final Block source;
    private final Block newState;

    /**
     * Creates a new {@link BlockEvent}
     *
     * @param block which represents the block which comes with this event
     */
    public BlockSpreadEvent( Block block, Block source, Block newState ) {
        super( block );
        this.source = source;
        this.newState = newState;
    }

    public Block getSource() {
        return this.source;
    }

    public Block getNewState() {
        return this.newState;
    }
}
