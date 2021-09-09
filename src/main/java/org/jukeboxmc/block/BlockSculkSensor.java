package org.jukeboxmc.block;

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

    public void setPowered( boolean value ) {
        this.setState( "powered_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isPowered() {
        return this.stateExists( "powered_bit" ) && this.getByteState( "powered_bit" ) == 1;
    }
}
