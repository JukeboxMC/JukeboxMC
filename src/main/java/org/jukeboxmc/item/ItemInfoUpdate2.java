package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockInfoUpdate2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemInfoUpdate2 extends Item {

    public ItemInfoUpdate2() {
        super( "minecraft:info_update2", 249 );
    }

    @Override
    public BlockInfoUpdate2 getBlock() {
        return new BlockInfoUpdate2();
    }
}
