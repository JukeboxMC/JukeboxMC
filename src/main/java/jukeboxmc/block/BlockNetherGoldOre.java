package jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherGoldOre;

public class BlockNetherGoldOre extends Block {

    public BlockNetherGoldOre() {
        super("minecraft:nether_gold_ore");
    }

    @Override
    public ItemNetherGoldOre toItem() {
        return new ItemNetherGoldOre();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_GOLD_ORE;
    }

}