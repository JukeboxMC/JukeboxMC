package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemSculk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSculk extends Block {

    public BlockSculk() {
        super( "minecraft:sculk" );
    }

    @Override
    public ItemSculk toItem() {
        return new ItemSculk();
    }

    @Override
    public BlockType getType() {
        return BlockType.SCULK;
    }
}
