package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBirchTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchTrapdoor extends Item {

    public ItemBirchTrapdoor() {
        super( "minecraft:birch_trapdoor", -146 );
    }

    @Override
    public Block getBlock() {
        return new BlockBirchTrapdoor();
    }
}
