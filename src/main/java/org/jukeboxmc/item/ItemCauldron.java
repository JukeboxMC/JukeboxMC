package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockCauldron;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCauldron extends Item {

    public ItemCauldron() {
        super ( "minecraft:cauldron" );
    }

    @Override
    public BlockCauldron getBlock() {
        return new BlockCauldron();
    }
}
