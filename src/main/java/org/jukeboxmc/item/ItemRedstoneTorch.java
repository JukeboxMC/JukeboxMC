package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockRedstoneTorch;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedstoneTorch extends Item {

    public ItemRedstoneTorch() {
        super ( "minecraft:redstone_torch" );
    }

    @Override
    public BlockRedstoneTorch getBlock() {
        return new BlockRedstoneTorch();
    }
}
