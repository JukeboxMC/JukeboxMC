package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralFanHang2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanHang2 extends Item {

    public ItemCoralFanHang2() {
        super( "minecraft:coral_fan_hang2", -136 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralFanHang2();
    }
}
