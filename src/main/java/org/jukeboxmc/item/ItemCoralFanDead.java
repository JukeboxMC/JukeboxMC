package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralFanDead;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.CoralColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralFanDead extends Item {

    public ItemCoralFanDead( int blockRuntimeId ) {
        super( "minecraft:coral_fan_dead", blockRuntimeId );
    }

    @Override
    public BlockCoralFanDead getBlock() {
        return (BlockCoralFanDead) BlockType.getBlock( this.blockRuntimeId );
    }

    public CoralColor getCoralType() {
        return this.getBlock().getCoralColor();
    }

}
