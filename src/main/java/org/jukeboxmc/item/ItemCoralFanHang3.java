package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralFanHang3;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang3 extends Item {

    public ItemCoralFanHang3() {
        super( "minecraft:coral_fan_hang3", -137 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralFanHang3();
    }
}
