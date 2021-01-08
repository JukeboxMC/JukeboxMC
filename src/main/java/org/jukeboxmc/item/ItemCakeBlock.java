package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCake;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCakeBlock extends Item {

    public ItemCakeBlock() {
        super( "minecraft:item.cake", 92 );
    }

    @Override
    public BlockCake getBlock() {
        return new BlockCake();
    }
}
