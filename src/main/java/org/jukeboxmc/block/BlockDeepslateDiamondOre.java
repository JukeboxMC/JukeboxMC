package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateDiamondOre;

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
}
