package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCoralFanDead;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanDead extends Item {

    public ItemCoralFanDead() {
        super( "minecraft:coral_fan_dead", -134 );
    }

    @Override
    public Block getBlock() {
        return new BlockCoralFanDead();
    }
}
