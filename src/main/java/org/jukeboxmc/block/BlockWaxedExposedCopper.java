package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedExposedCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedExposedCopper extends Block{

    public BlockWaxedExposedCopper() {
        super( "minecraft:waxed_exposed_copper" );
    }

    @Override
    public ItemWaxedExposedCopper toItem() {
        return new ItemWaxedExposedCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.WAXED_EXPOSED_COPPER;
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
