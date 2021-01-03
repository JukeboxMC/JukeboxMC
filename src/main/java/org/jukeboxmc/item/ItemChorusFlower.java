package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockChorusFlower;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChorusFlower extends Item {

    public ItemChorusFlower() {
        super( "minecraft:chorus_flower", 200 );
    }

    @Override
    public Block getBlock() {
        return new BlockChorusFlower();
    }
}
