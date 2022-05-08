package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemMelonBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMelonBlock extends Block {

    public BlockMelonBlock() {
        super( "minecraft:melon_block" );
    }

    @Override
    public ItemMelonBlock toItem() {
        return new ItemMelonBlock();
    }

    @Override
    public BlockType getType() {
        return BlockType.MELON_BLOCK;
    }

}
