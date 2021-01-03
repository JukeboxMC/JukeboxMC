package org.jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockCrimsonFungus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonFungus extends Item {

    public ItemCrimsonFungus() {
        super( "minecraft:crimson_fungus", -228 );
    }

    @Override
    public Block getBlock() {
        return new BlockCrimsonFungus();
    }
}
