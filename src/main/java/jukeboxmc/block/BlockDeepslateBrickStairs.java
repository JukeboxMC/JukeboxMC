package jukeboxmc.block;

import org.jukeboxmc.item.ItemDeepslateBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDeepslateBrickStairs extends BlockStairs {

    public BlockDeepslateBrickStairs() {
        super( "minecraft:deepslate_brick_stairs" );
    }

    @Override
    public ItemDeepslateBrickStairs toItem() {
        return new ItemDeepslateBrickStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DEEPSLATE_BRICK_STAIRS;
    }
}
