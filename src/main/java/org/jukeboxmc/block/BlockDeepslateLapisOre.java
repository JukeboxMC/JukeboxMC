package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateLapisOre;
import org.jukeboxmc.item.type.ItemTierType;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateLapisOre extends Block {

    public BlockDeepslateLapisOre() {
        super( "minecraft:deepslate_lapis_ore" );
    }

    @Override
    public ItemDeepslateLapisOre toItem() {
        return new ItemDeepslateLapisOre();
    }

    @Override
    public BlockType getType() {
        return BlockType.DEEPSLATE_LAPIS_ORE;
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
