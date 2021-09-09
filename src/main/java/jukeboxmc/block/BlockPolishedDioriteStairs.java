package jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedDioriteStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPolishedDioriteStairs extends BlockStairs {

    public BlockPolishedDioriteStairs() {
        super( "minecraft:polished_diorite_stairs" );
    }

    @Override
    public ItemPolishedDioriteStairs toItem() {
        return new ItemPolishedDioriteStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_DIORITE_STAIRS;
    }

}
