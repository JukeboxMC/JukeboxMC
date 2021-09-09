package jukeboxmc.block;

import org.jukeboxmc.item.ItemWallSign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakWallSign extends BlockWallSign {

    public BlockOakWallSign() {
        super( "minecraft:wall_sign" );
    }

    @Override
    public ItemWallSign toItem() {
        return new ItemWallSign();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WALL_SIGN;
    }

}
