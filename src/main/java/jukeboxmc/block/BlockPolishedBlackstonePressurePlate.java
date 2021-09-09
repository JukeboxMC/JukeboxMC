package jukeboxmc.block;

import org.jukeboxmc.item.ItemPolishedBlackstonePressurePlate;

public class BlockPolishedBlackstonePressurePlate extends BlockPressurePlate {

    public BlockPolishedBlackstonePressurePlate() {
        super("minecraft:polished_blackstone_pressure_plate");
    }

    @Override
    public ItemPolishedBlackstonePressurePlate toItem() {
        return new ItemPolishedBlackstonePressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.POLISHED_BLACKSTONE_PRESSURE_PLATE;
    }

}