package jukeboxmc.block;

import org.jukeboxmc.item.ItemSpruceStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceStairs extends BlockStairs {

    public BlockSpruceStairs() {
        super( "minecraft:spruce_stairs" );
    }

    @Override
    public ItemSpruceStairs toItem() {
        return new ItemSpruceStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SPRUCE_STAIRS;
    }

}
