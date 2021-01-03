package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralFanHang;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang extends Item {

    public ItemCoralFanHang() {
        super( "minecraft:coral_fan_hang", -135 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralFanHang();
    }
}
