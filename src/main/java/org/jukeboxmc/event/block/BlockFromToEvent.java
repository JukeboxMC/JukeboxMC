package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFromToEvent extends BlockEvent implements Cancellable {

    private Block blockTo;

    public BlockFromToEvent( Block block, Block blockTo ) {
        super( block );

        this.blockTo = blockTo;
    }

    public Block getBlockFrom() {
        return super.getBlock();
    }

    public Block getBlockTo() {
        return this.blockTo;
    }

    public void setBlockTo( Block blockTo ) {
        this.blockTo = blockTo;
    }
}