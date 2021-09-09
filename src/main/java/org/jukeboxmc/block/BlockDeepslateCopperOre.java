package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateCopperOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateCopperOre extends Block{

    public BlockDeepslateCopperOre() {
        super( "minecraft:deepslate_copper_ore" );
    }

    @Override
    public ItemDeepslateCopperOre toItem() {
        return new ItemDeepslateCopperOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_COPPER_ORE;
    }
}
