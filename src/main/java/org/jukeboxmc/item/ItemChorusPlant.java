package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockChorusFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusPlant extends Item {

    public ItemChorusPlant() {
        super( "minecraft:chorus_plant", 240 );
    }

    @Override
    public BlockChorusFlower getBlock() {
        return new BlockChorusFlower();
    }
}
