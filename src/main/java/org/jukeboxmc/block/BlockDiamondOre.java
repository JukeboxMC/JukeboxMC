package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDiamondOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDiamondOre extends Block {

    public BlockDiamondOre() {
        super( "minecraft:diamond_ore" );
    }

    @Override
    public ItemDiamondOre toItem() {
        return new ItemDiamondOre();
    }

    @Override
    public BlockType getType() {
        return BlockType.DIAMOND_ORE;
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
