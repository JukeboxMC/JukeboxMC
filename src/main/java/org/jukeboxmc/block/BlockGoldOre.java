package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemGoldOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockGoldOre extends Block {

    public BlockGoldOre() {
        super( "minecraft:gold_ore" );
    }

    @Override
    public ItemGoldOre toItem() {
        return new ItemGoldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GOLD_ORE;
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
        return ItemTierType.IRON;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

}
