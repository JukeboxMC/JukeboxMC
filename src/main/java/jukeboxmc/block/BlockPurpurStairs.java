package jukeboxmc.block;

import org.jukeboxmc.item.ItemPurpurStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPurpurStairs extends BlockStairs {

    public BlockPurpurStairs() {
        super( "minecraft:purpur_stairs" );
    }

    @Override
    public ItemPurpurStairs toItem() {
        return new ItemPurpurStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PURPUR_STAIRS;
    }

}
