package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFanHang;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang extends Item {

    public ItemCoralFanHang() {
        super(  -135 );
    }

    @Override
    public BlockCoralFanHang getBlock() {
        return new BlockCoralFanHang();
    }
}
