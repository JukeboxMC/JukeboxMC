package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedOxidizedCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCopper extends Block {

    public BlockWaxedOxidizedCopper() {
        super( "minecraft:waxed_oxidized_copper" );
    }

    @Override
    public ItemWaxedOxidizedCopper toItem() {
        return new ItemWaxedOxidizedCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_COPPER;
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
