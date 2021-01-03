package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBamboo;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBamboo extends Item {

    public ItemBamboo() {
        super( "minecraft:bamboo", -163 );
    }

    @Override
    public Block getBlock() {
        return new BlockBamboo();
    }
}
