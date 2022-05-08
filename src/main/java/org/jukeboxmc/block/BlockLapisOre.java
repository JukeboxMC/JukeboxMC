package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemLapisOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLapisOre extends Block {

    public BlockLapisOre() {
        super( "minecraft:lapis_ore" );
    }

    @Override
    public ItemLapisOre toItem() {
        return new ItemLapisOre();
    }

    @Override
    public BlockType getType() {
        return BlockType.LAPIS_ORE;
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
