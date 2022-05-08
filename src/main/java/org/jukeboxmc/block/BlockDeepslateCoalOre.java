package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateCoalOre;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateCoalOre extends Block {

    public BlockDeepslateCoalOre() {
        super( "minecraft:deepslate_coal_ore" );
    }

    @Override
    public ItemDeepslateCoalOre toItem() {
        return new ItemDeepslateCoalOre();
    }

    @Override
    public BlockType getType() {
        return BlockType.DEEPSLATE_COAL_ORE;
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
    public boolean canBreakWithHand() {
        return false;
    }
}
