package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockChorusPlant;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusPlant extends Item {

    public ItemChorusPlant() {
        super( "minecraft:chorus_plant", 240 );
    }

    @Override
    public Block getBlock() {
        return new BlockChorusPlant();
    }
}
