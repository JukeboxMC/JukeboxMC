package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBed;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBedBlock extends Item {

    public ItemBedBlock() {
        super ( "minecraft:item.bed" );
    }

    @Override
    public BlockBed getBlock() {
        return new BlockBed();
    }
}
