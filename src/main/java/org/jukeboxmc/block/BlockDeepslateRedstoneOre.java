package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateRedstoneOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateRedstoneOre extends Block {

    public BlockDeepslateRedstoneOre() {
        super( "minecraft:deepslate_redstone_ore" );
    }

    @Override
    public ItemDeepslateRedstoneOre toItem() {
        return new ItemDeepslateRedstoneOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_REDSTONE_ORE;
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
