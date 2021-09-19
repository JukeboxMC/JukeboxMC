package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCopperOre;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCopperOre extends Block {

    public BlockCopperOre() {
        super( "minecraft:copper_ore" );
    }

    @Override
    public ItemCopperOre toItem() {
        return new ItemCopperOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COPPER_ORE;
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
