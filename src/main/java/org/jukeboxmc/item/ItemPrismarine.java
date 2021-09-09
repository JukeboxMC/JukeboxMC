package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPrismarine;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.PrismarineType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarine extends Item {

    public ItemPrismarine( int blockRuntimeId ) {
        super( "minecraft:prismarine", blockRuntimeId );
    }

    @Override
    public BlockPrismarine getBlock() {
        return (BlockPrismarine) BlockType.getBlock( this.blockRuntimeId );
    }

    public PrismarineType getPrismarineType() {
        return this.getBlock().getPrismarineType();
    }

}
