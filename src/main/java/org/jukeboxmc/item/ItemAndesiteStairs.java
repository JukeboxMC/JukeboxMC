package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAndesiteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAndesiteStairs extends Item {

    public ItemAndesiteStairs() {
        super( "minecraft:andesite_stairs", -171 );
    }

    @Override
    public Block getBlock() {
        return new BlockAndesiteStairs();
    }
}
