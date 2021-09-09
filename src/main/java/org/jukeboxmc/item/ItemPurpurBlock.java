package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPurpurBlock;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.PurpurType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpurBlock extends Item {

    public ItemPurpurBlock( int blockRuntimeId ) {
        super( "minecraft:purpur_block", blockRuntimeId );
    }

    @Override
    public BlockPurpurBlock getBlock() {
        return (BlockPurpurBlock) BlockType.getBlock( this.blockRuntimeId );
    }

    public PurpurType getPurpurType() {
        return this.getBlock().getPurpurType();
    }


}
