package jukeboxmc.block;

import org.jukeboxmc.item.ItemEndBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEndBrickStairs extends BlockStairs {

    public BlockEndBrickStairs() {
        super( "minecraft:end_brick_stairs" );
    }

    @Override
    public ItemEndBrickStairs toItem() {
        return new ItemEndBrickStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.END_BRICK_STAIRS;
    }

}
