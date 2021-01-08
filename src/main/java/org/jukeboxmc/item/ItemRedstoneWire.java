package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneWire;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneWire extends Item {

    public ItemRedstoneWire() {
        super( "minecraft:redstone_wire", 55 );
    }

    @Override
    public BlockRedstoneWire getBlock() {
        return new BlockRedstoneWire();
    }
}
