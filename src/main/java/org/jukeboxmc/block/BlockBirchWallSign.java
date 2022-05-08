package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBirchWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchWallSign extends BlockWallSign {

    public BlockBirchWallSign() {
        super( "minecraft:birch_wall_sign" );
    }

    @Override
    public ItemBirchWallSign toItem() {
        return new ItemBirchWallSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.BIRCH_WALL_SIGN;
    }

}
