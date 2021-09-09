package jukeboxmc.block;

import org.jukeboxmc.item.ItemQuartzStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockQuartzStairs extends BlockStairs {

    public BlockQuartzStairs() {
        super( "minecraft:quartz_stairs" );
    }

    @Override
    public ItemQuartzStairs toItem() {
        return new ItemQuartzStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_STAIRS;
    }

}
