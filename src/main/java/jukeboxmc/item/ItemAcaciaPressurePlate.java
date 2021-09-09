package jukeboxmc.item;

import org.jukeboxmc.block.BlockAcaciaPressurePlate;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAcaciaPressurePlate extends Item {

    public ItemAcaciaPressurePlate() {
        super( "minecraft:acacia_pressure_plate" );
    }

    @Override
    public BlockAcaciaPressurePlate getBlock() {
        return new BlockAcaciaPressurePlate();
    }
}
