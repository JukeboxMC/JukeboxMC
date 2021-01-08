package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMossyCobblestoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossyCobblestoneStairs extends Item {

    public ItemMossyCobblestoneStairs() {
        super( "minecraft:mossy_cobblestone_stairs", -179 );
    }

    @Override
    public BlockMossyCobblestoneStairs getBlock() {
        return new BlockMossyCobblestoneStairs();
    }
}
