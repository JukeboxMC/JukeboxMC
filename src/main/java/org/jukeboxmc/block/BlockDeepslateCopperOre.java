package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateCopperOre;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateCopperOre extends Block{

    public BlockDeepslateCopperOre() {
        super( "minecraft:deepslate_copper_ore" );
    }

    @Override
    public ItemDeepslateCopperOre toItem() {
        return new ItemDeepslateCopperOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_COPPER_ORE;
    }

    @Override
    public double getHardness() {
        return 4.5;
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
