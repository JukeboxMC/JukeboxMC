package jukeboxmc.block;

import org.jukeboxmc.item.ItemCrimsonTrapdoor;

public class BlockCrimsonTrapdoor extends BlockTrapdoor {

    public BlockCrimsonTrapdoor() {
        super("minecraft:crimson_trapdoor");
    }

    @Override
    public ItemCrimsonTrapdoor toItem() {
        return new ItemCrimsonTrapdoor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CRIMSON_TRAPDOOR;
    }

}