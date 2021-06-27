package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneWire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstone extends Item {

    public ItemRedstone() {
        super ( "minecraft:redstone" );
    }

    @Override
    public BlockRedstoneWire getBlock() {
        return new BlockRedstoneWire();
    }
}
