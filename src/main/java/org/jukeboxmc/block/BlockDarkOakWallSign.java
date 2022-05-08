package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDarkOakWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDarkOakWallSign extends BlockWallSign {

    public BlockDarkOakWallSign() {
        super( "minecraft:darkoak_wall_sign" );
    }

    @Override
    public ItemDarkOakWallSign toItem() {
        return new ItemDarkOakWallSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.DARK_OAK_WALL_SIGN;
    }

}
