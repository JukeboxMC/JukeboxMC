package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.RotationDirection;
import org.jukeboxmc.block.type.CoralColor;
import org.jukeboxmc.item.ItemCoralFan;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralFan extends Block {

    public BlockCoralFan() {
        super( "minecraft:coral_fan" );
    }

    @Override
    public ItemCoralFan toItem() {
        return new ItemCoralFan( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_FAN;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setCoralDirection( RotationDirection rotationDirection ) {
        this.setState( "coral_fan_direction", rotationDirection.ordinal() );
    }

    public RotationDirection getRotationDirection() {
        return this.stateExists( "coral_fan_direction" ) ? RotationDirection.values()[this.getIntState( "coral_fan_direction" )] : RotationDirection.EAST_WEST;
    }

    public BlockCoralFan setCoralColor( CoralColor coralColor ) {
        return this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ) ) : CoralColor.BLUE;
    }
}
