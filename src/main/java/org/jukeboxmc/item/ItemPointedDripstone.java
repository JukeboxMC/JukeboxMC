package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockPointedDripstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPointedDripstone extends Item{

    public ItemPointedDripstone() {
        super( "minecraft:pointed_dripstone" );
    }

    @Override
    public BlockPointedDripstone getBlock() {
        return new BlockPointedDripstone();
    }
}
