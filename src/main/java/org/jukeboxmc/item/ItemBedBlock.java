package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBed;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBedBlock extends Item {

    public ItemBedBlock() {
        super( "minecraft:item.bed", 26 );
    }

    @Override
    public Block getBlock() {
        return new BlockBed();
    }
}
