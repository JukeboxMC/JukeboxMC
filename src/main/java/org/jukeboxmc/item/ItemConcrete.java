package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockConcrete;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemConcrete extends Item {

    public ItemConcrete() {
        super( "minecraft:concrete", 236 );
    }

    @Override
    public Block getBlock() {
        return new BlockConcrete();
    }
}
