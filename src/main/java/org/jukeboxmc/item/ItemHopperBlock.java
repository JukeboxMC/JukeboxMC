package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHopperBlock extends Item {

    public ItemHopperBlock() {
        super( "minecraft:item.hopper", 154 );
    }

    @Override
    public BlockHopper getBlock() {
        return new BlockHopper();
    }
}
