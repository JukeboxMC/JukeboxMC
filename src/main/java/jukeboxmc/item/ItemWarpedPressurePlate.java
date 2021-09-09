package jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedPressurePlate extends Item {

    public ItemWarpedPressurePlate() {
        super ( "minecraft:warped_pressure_plate" );
    }

    @Override
    public BlockWarpedPressurePlate getBlock() {
        return new BlockWarpedPressurePlate();
    }
}
