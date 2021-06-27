package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemSculkSensor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSculkSensor extends Block{

    public BlockSculkSensor() {
        super( "minecraft:sculk_sensor" );
    }

    @Override
    public ItemSculkSensor toItem() {
        return new ItemSculkSensor();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.SCULK_SENSOR;
    }
}
