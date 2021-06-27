package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemCrackedDeepslateBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrackedDeepslateBricks extends Block {

    public BlockCrackedDeepslateBricks() {
        super( "minecraft:cracked_deepslate_bricks" );
    }

    @Override
    public ItemCrackedDeepslateBricks toItem() {
        return new ItemCrackedDeepslateBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRACKED_DEEPSLATE_BRICKS;
    }
}
