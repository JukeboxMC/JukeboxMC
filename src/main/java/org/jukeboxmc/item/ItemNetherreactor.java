package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherreactor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherreactor extends Item {

    public ItemNetherreactor() {
        super( "minecraft:netherreactor" );
    }

    @Override
    public BlockNetherreactor getBlock() {
        return new BlockNetherreactor();
    }
}
