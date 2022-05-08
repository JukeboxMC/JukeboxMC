package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemStonecutter;
import org.jukeboxmc.item.type.ItemToolType;

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
    public BlockType getType() {
        return BlockType.STONECUTTER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 3.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }
}
