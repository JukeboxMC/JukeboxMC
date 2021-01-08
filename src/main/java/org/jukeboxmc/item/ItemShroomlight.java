package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockShroomlight;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemShroomlight extends Item {

    public ItemShroomlight() {
        super( "minecraft:shroomlight", -230 );
    }

    @Override
    public BlockShroomlight getBlock() {
        return new BlockShroomlight();
    }
}
