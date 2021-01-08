package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBorderBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBorderBlock extends Item {

    public ItemBorderBlock() {
        super( "minecraft:border_block", 212 );
    }

    @Override
    public BlockBorderBlock getBlock() {
        return new BlockBorderBlock();
    }
}
