package jukeboxmc.block;

import org.jukeboxmc.item.ItemCrackedPolishedBlackstoneBricks;

public class BlockCrackedPolishedBlackstoneBricks extends Block {

    public BlockCrackedPolishedBlackstoneBricks() {
        super("minecraft:cracked_polished_blackstone_bricks");
    }

    @Override
    public ItemCrackedPolishedBlackstoneBricks toItem() {
        return new ItemCrackedPolishedBlackstoneBricks();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRACKED_POLISHED_BLACKSTONE_BRICKS;
    }

}