package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemDeepslateLapisOre;

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
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_LAPIS_ORE;
    }
}
