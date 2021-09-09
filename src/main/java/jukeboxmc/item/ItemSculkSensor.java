package jukeboxmc.item;

import org.jukeboxmc.block.BlockSculkSensor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSculkSensor extends Item{

    public ItemSculkSensor() {
        super( "minecraft:sculk_sensor" );
    }

    @Override
    public BlockSculkSensor getBlock() {
        return new BlockSculkSensor();
    }
}
