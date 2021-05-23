package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTarget;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTarget extends Item {

    public ItemTarget() {
        super( -239 );
    }

    @Override
    public BlockTarget getBlock() {
        return new BlockTarget();
    }
}
