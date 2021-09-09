package jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedDeepslateStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDeepslateStairs extends Block {

    public BlockPolishedDeepslateStairs() {
        super( "minecraft:polished_deepslate_stairs" );
    }

    @Override
    public ItemPolishedDeepslateStairs toItem() {
        return new ItemPolishedDeepslateStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_DEEPSLATE_STAIRS;
    }
}
