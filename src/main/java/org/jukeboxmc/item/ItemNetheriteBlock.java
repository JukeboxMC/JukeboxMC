package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetheriteBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetheriteBlock extends Item {

    public ItemNetheriteBlock() {
        super( "minecraft:netherite_block", -270 );
    }

    @Override
    public BlockNetheriteBlock getBlock() {
        return new BlockNetheriteBlock();
    }
}
