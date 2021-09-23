package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateDiamondOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateDiamondOre extends Block {

    public BlockDeepslateDiamondOre() {
        super( "minecraft:deepslate_diamond_ore" );
    }

    @Override
    public ItemDeepslateDiamondOre toItem() {
        return new ItemDeepslateDiamondOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_DIAMOND_ORE;
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
        return ItemTierType.IRON;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }
}
