package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleTrapdoor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleTrapdoor extends Item {

    public ItemJungleTrapdoor() {
        super( "minecraft:jungle_trapdoor", -148 );
    }

    @Override
    public BlockJungleTrapdoor getBlock() {
        return new BlockJungleTrapdoor();
    }
}
