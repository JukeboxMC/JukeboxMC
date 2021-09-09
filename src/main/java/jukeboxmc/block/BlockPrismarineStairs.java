package jukeboxmc.block;

import org.jukeboxmc.item.ItemPrismarineStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarineStairs extends BlockStairs {

    public BlockPrismarineStairs() {
        super( "minecraft:prismarine_stairs" );
    }

    @Override
    public ItemPrismarineStairs toItem() {
        return new ItemPrismarineStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PRISMARINE_STAIRS;
    }

}
