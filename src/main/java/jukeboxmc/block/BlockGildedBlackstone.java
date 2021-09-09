package jukeboxmc.block;

import org.jukeboxmc.item.ItemGlidedBlackstone;

public class BlockGildedBlackstone extends Block {

    public BlockGildedBlackstone() {
        super("minecraft:gilded_blackstone");
    }

    @Override
    public ItemGlidedBlackstone toItem() {
        return new ItemGlidedBlackstone();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GLIDED_BLACKSTONE;
    }

}