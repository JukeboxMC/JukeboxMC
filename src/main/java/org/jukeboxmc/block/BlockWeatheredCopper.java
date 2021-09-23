package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWeatheredCopper;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredCopper extends Block {

    public BlockWeatheredCopper() {
        super( "minecraft:weathered_copper" );
    }

    @Override
    public ItemWeatheredCopper toItem() {
        return new ItemWeatheredCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEATHERED_COPPER;
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
