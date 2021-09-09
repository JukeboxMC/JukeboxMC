package jukeboxmc.block;

import org.jukeboxmc.item.ItemChiseledNetherBricks;

public class BlockChiseledNetherBricks extends Block {

    public BlockChiseledNetherBricks() {
        super("minecraft:chiseled_nether_bricks");
    }

    @Override
    public ItemChiseledNetherBricks toItem() {
        return new ItemChiseledNetherBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CHISELED_NETHER_BRICKS;
    }

}