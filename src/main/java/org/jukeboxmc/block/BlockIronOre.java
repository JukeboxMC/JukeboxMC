package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemIronOre;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockIronOre extends Block {

    public BlockIronOre() {
        super( "minecraft:iron_ore" );
    }

    @Override
    public ItemIronOre toItem() {
        return new ItemIronOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.IRON_ORE;
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
