package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemInfoUpdate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockInfoUpdate extends Block {

    public BlockInfoUpdate() {
        super( "minecraft:info_update" );
    }

    @Override
    public ItemInfoUpdate toItem() {
        return new ItemInfoUpdate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.INFO_UPDATE;
    }

}
