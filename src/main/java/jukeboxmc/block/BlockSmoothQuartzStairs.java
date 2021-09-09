package jukeboxmc.block;

import org.jukeboxmc.item.ItemSmoothQuartzStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSmoothQuartzStairs extends BlockStairs {

    public BlockSmoothQuartzStairs() {
        super( "minecraft:smooth_quartz_stairs" );
    }

    @Override
    public ItemSmoothQuartzStairs toItem() {
        return new ItemSmoothQuartzStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SMOOTH_QUARTZ_STAIRS;
    }

}
