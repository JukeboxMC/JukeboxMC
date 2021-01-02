package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBedrock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBedrock extends Item {

    public ItemBedrock() {
        super( "minecraft:bedrock", 7 );
    }

    @Override
    public Block getBlock() {
        return new BlockBedrock();
    }
}
