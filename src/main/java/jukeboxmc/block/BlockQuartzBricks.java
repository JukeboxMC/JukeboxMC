package jukeboxmc.block;

import org.jukeboxmc.item.ItemQuartzBricks;

public class BlockQuartzBricks extends Block {

    public BlockQuartzBricks() {
        super("minecraft:quartz_bricks");
    }

    @Override
    public ItemQuartzBricks toItem() {
        return new ItemQuartzBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.QUARTZ_BRICKS;
    }

}