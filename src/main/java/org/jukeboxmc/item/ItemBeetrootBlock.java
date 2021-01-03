package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockBeetroot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeetrootBlock extends Item {

    public ItemBeetrootBlock() {
        super( "minecraft:item.beetroot", 244 );
    }

    @Override
    public Block getBlock() {
        return new BlockBeetroot();
    }
}
