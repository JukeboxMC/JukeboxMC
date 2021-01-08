package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockBirchWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBirchWallSign extends Item {

    public ItemBirchWallSign() {
        super( "minecraft:birch_wall_sign", -187 );
    }

    @Override
    public BlockBirchWallSign getBlock() {
        return new BlockBirchWallSign();
    }
}
