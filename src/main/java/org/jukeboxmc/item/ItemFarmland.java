package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockFarmland;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemFarmland extends Item {

    public ItemFarmland() {
        super( 60 );
    }

    @Override
    public BlockFarmland getBlock() {
        return new BlockFarmland();
    }
}
