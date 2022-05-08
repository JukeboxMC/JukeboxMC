package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemExposedCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCopper extends Block{

    public BlockExposedCopper() {
        super( "minecraft:exposed_copper" );
    }

    @Override
    public ItemExposedCopper toItem() {
        return new ItemExposedCopper();
    }

    @Override
    public BlockType getType() {
        return BlockType.EXPOSED_COPPER;
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
