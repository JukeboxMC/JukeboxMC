package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.item.ItemCoralFanHang;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralFanHang extends Block {

    public BlockCoralFanHang() {
        super( "minecraft:coral_fan_hang" );
    }

    @Override
    public ItemCoralFanHang toItem() {
        return new ItemCoralFanHang();
    }

    @Override
    public BlockType getType() {
        return BlockType.CORAL_FAN_HANG;
    }

    public void setCoralHangType( boolean value ) {
        this.setState( "coral_hang_type_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isCoralHangType() {
        return this.stateExists( "coral_hang_type_bit" ) && this.getByteState( "coral_hang_type_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        this.setState( "coral_direction", direction.ordinal() );
    }

    public Direction getDirection() {
        return this.stateExists( "coral_direction" ) ? Direction.values()[this.getIntState( "coral_direction" )] : Direction.SOUTH;
    }

    public void setDead( boolean value ) {
        this.setState( "dead_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
