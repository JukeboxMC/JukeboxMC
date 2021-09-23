package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateIronOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateIronOre extends Block {

    public BlockDeepslateIronOre() {
        super( "minecraft:deepslate_iron_ore" );
    }

    @Override
    public ItemDeepslateIronOre toItem() {
        return new ItemDeepslateIronOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_IRON_ORE;
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
