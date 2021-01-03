package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBambooSapling;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBambooSapling extends Item {

    public ItemBambooSapling() {
        super( "minecraft:bamboo_saplin", -164 );
    }

    @Override
    public Block getBlock() {
        return new BlockBambooSapling();
    }
}
