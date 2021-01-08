package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHoneycombBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHoneycomBlock extends Item {

    public ItemHoneycomBlock() {
        super( "minecraft:honeycomb_block", -221 );
    }

    @Override
    public BlockHoneycombBlock getBlock() {
        return new BlockHoneycombBlock();
    }
}
