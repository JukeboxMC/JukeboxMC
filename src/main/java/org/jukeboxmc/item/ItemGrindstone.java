package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockGrindstone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGrindstone extends Item {

    public ItemGrindstone() {
        super( "minecraft:grindstone", -195 );
    }

    @Override
    public BlockGrindstone getBlock() {
        return new BlockGrindstone();
    }
}
