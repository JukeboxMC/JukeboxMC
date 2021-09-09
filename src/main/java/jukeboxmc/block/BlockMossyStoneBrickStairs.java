package jukeboxmc.block;

import org.jukeboxmc.item.ItemMossyStoneBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockMossyStoneBrickStairs extends BlockStairs {

    public BlockMossyStoneBrickStairs() {
        super( "minecraft:mossy_stone_brick_stairs" );
    }

    @Override
    public ItemMossyStoneBrickStairs toItem() {
        return new ItemMossyStoneBrickStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MOSSY_STONE_BRICK_STAIRS;
    }

}
