package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.item.ItemWaxedWeatheredCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedWeatheredCopper extends Block{

    public BlockWaxedWeatheredCopper() {
        super( "minecraft:waxed_weathered_copper" );
    }

    @Override
    public ItemWaxedWeatheredCopper toItem() {
        return new ItemWaxedWeatheredCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_WEATHERED_COPPER;
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
