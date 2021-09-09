package jukeboxmc.block;

import org.jukeboxmc.item.ItemCryingObsidian;

public class BlockCryingObsidian extends Block {

    public BlockCryingObsidian() {
        super("minecraft:crying_obsidian");
    }

    @Override
    public ItemCryingObsidian toItem() {
        return new ItemCryingObsidian();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRYING_OBSIDIAN;
    }

}