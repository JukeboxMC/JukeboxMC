package jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedAndesiteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedAndesiteStairs extends BlockStairs {

    public BlockPolishedAndesiteStairs() {
        super( "minecraft:polished_andesite_stairs" );
    }

    @Override
    public ItemPolishedAndesiteStairs toItem() {
        return new ItemPolishedAndesiteStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_ANDESITE_STAIRS;
    }

}
