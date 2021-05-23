package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDragonEgg;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDragonEgg extends Block {

    public BlockDragonEgg() {
        super( "minecraft:dragon_egg" );
    }

    @Override
    public ItemDragonEgg toItem() {
        return new ItemDragonEgg();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DRAGON_EGG;
    }

}
