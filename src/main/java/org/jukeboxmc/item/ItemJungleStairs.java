package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleStairs extends Item {

    public ItemJungleStairs() {
        super( "minecraft:jungle_stairs", 136 );
    }

    @Override
    public BlockJungleStairs getBlock() {
        return new BlockJungleStairs();
    }
}
