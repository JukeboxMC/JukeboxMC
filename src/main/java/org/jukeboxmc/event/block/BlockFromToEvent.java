package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFromToEvent extends BlockEvent implements Cancellable {

    private Block blockTo;

    /**
     * Creates a new {@link BlockFromToEvent}
     *
     * @param block   which represents the block from
     * @param blockTo which stands for the block which was changed
     */
    public BlockFromToEvent( Block block, Block blockTo ) {
        super( block );

        this.blockTo = blockTo;
    }

    /**
     * Retrieves the original block
     *
     * @return a fresh {@link Block}
     */
    public Block getBlockFrom() {
        return super.getBlock();
    }

    /**
     * Retrieves the result bock which was affected with the event
     *
     * @return a fresh {@link Block}
     */
    public Block getBlockTo() {
        return this.blockTo;
    }

    /**
     * Modifies the result block
     *
     * @param blockTo which should be modified
     */
    public void setBlockTo( Block blockTo ) {
        this.blockTo = blockTo;
    }
}