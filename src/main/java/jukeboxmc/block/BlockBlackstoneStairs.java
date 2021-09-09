package jukeboxmc.block;

import org.jukeboxmc.item.ItemBlackstoneStairs;

public class BlockBlackstoneStairs extends BlockStairs {

    public BlockBlackstoneStairs() {
        super("minecraft:blackstone_stairs");
    }

    @Override
    public ItemBlackstoneStairs toItem() {
        return new ItemBlackstoneStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLACKSTONE_STAIRS;
    }

}