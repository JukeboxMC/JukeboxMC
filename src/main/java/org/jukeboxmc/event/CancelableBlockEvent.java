package org.jukeboxmc.event;

import org.jukeboxmc.block.Block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CancelableBlockEvent extends CancelableEvent {

    private Block block;

    public CancelableBlockEvent( Block block ) {
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }
}
