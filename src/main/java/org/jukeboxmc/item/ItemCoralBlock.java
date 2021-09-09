package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCoralBlock;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.CoralColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCoralBlock extends Item {

    public ItemCoralBlock( int blockRuntimeId ) {
        super( "minecraft:coral_block", blockRuntimeId );
    }

    @Override
    public BlockCoralBlock getBlock() {
        return (BlockCoralBlock) BlockType.getBlock( this.blockRuntimeId );
    }

    public CoralColor getCoralType() {
        return this.getBlock().getCoralColor();
    }

}
