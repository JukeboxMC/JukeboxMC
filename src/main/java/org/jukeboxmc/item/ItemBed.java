package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBed;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBed extends Item {

    public ItemBed() {
        super( "minecraft:bed", 416 );
    }

    @Override
    public Block getBlock() {
        return new BlockBed();
    }
}
