package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemInfoUpdate2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfoUpdate2 extends Block {

    public BlockInfoUpdate2() {
        super( "minecraft:info_update2" );
    }

    @Override
    public ItemInfoUpdate2 toItem() {
        return new ItemInfoUpdate2();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFO_UPDATE2;
    }

}
