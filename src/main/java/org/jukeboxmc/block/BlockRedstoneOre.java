package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedstoneOre;
import org.jukeboxmc.item.ItemTierType;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedstoneOre extends Block {

    public BlockRedstoneOre() {
        super( "minecraft:redstone_ore" );
    }

    @Override
    public ItemRedstoneOre toItem() {
        return new ItemRedstoneOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.REDSTONE_ORE;
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
