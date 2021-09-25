package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSpruceWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSpruceWallSign extends Item {

    public ItemSpruceWallSign() {
        super ( "minecraft:spruce_wall_sign" );
    }

    @Override
    public BlockSpruceWallSign getBlock() {
        return new BlockSpruceWallSign();
    }

    @Override
    public int getMaxAmount() {
        return 16;
    }
}
