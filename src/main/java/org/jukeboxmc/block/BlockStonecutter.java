package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemStonecutter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStonecutter extends BlockWaterlogable {

    public BlockStonecutter() {
        super( "minecraft:stonecutter" );
    }

    @Override
    public ItemStonecutter toItem() {
        return new ItemStonecutter();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.STONECUTTER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
