package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStainedHardenedClay;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.type.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStainedHardenedClay extends Item {

    public ItemStainedHardenedClay( int blockRuntimeId ) {
        super( "minecraft:stained_hardened_clay", blockRuntimeId );
    }

    @Override
    public BlockStainedHardenedClay getBlock() {
        return (BlockStainedHardenedClay) BlockType.getBlock( this.blockRuntimeId );
    }

    public BlockColor getColor() {
        return this.getBlock().getColor();
    }

}
