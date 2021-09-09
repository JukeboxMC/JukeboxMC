package jukeboxmc.block;

import org.jukeboxmc.item.ItemNormalStoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNormalStoneStairs extends BlockStairs {

    public BlockNormalStoneStairs() {
        super( "minecraft:normal_stone_stairs" );
    }

    @Override
    public ItemNormalStoneStairs toItem() {
        return new ItemNormalStoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NORMAL_STONE_STAIRS;
    }

}
