package jukeboxmc.block;

import org.jukeboxmc.item.ItemJunglePressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJunglePressurePlate extends BlockPressurePlate {

    public BlockJunglePressurePlate() {
        super( "minecraft:jungle_pressure_plate" );
    }

    @Override
    public ItemJunglePressurePlate toItem() {
        return new ItemJunglePressurePlate();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.JUNGLE_PRESSURE_PLATE;
    }

}
