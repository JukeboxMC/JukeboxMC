package jukeboxmc.block;

import org.jukeboxmc.item.ItemSandstoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSandstoneStairs extends BlockStairs {

    public BlockSandstoneStairs() {
        super( "minecraft:sandstone_stairs" );
    }

    @Override
    public ItemSandstoneStairs toItem() {
        return new ItemSandstoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SANDSTONE_STAIRS;
    }

}
