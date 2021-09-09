package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneOre;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneOre extends Item {

    public ItemRedstoneOre() {
        super ( "minecraft:redstone_ore" );
    }

    @Override
    public BlockRedstoneOre getBlock() {
        return new BlockRedstoneOre();
    }
}
