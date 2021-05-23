package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFanHang2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang2 extends Item {

    public ItemCoralFanHang2() {
        super( -136 );
    }

    @Override
    public BlockCoralFanHang2 getBlock() {
        return new BlockCoralFanHang2();
    }
}
