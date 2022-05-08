package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemJungleWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleWallSign extends BlockWallSign {

    public BlockJungleWallSign() {
        super( "minecraft:jungle_wall_sign" );
    }

    @Override
    public ItemJungleWallSign toItem() {
        return new ItemJungleWallSign();
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_WALL_SIGN;
    }

}
