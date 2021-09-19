package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemOxidizedCopper;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOxidizedCopper extends Block{

    public BlockOxidizedCopper() {
        super( "minecraft:oxidized_copper" );
    }

    @Override
    public ItemOxidizedCopper toItem() {
        return new ItemOxidizedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.OXIDIZED_COPPER;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.PICKAXE;
    }

    @Override
    public ItemTierType getTierType() {
        return ItemTierType.STONE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
