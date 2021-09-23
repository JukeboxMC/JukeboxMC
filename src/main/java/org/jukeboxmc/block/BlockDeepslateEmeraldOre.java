package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateEmeraldOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateEmeraldOre extends Block{

    public BlockDeepslateEmeraldOre() {
        super( "minecraft:deepslate_emerald_ore" );
    }

    @Override
    public ItemDeepslateEmeraldOre toItem() {
        return new ItemDeepslateEmeraldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_EMERALD_ORE;
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
