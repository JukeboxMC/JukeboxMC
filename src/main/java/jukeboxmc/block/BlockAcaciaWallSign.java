package jukeboxmc.block;

import org.jukeboxmc.item.ItemAcaciaWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaWallSign extends BlockWallSign {

    public BlockAcaciaWallSign() {
        super( "minecraft:acacia_wall_sign" );
    }

    @Override
    public ItemAcaciaWallSign toItem() {
        return new ItemAcaciaWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACACIA_WALL_SIGN;
    }

}
