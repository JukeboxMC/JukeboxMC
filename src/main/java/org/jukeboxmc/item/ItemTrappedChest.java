package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockTrappedChest;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTrappedChest extends Item {

    public ItemTrappedChest() {
        super ( "minecraft:trapped_chest" );
    }

    @Override
    public BlockTrappedChest getBlock() {
        return new BlockTrappedChest();
    }
}
