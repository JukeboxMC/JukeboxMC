package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.RotationDirection;
import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralFanDead extends Block {

    public BlockCoralFanDead() {
        super( "minecraft:coral_fan_dead" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getCoralColor().ordinal() );
    }

    public void setCoralDirection( RotationDirection rotationDirection ) {
        this.setState( "coral_fan_direction", rotationDirection.ordinal() );
    }

    public RotationDirection getRotationDirection() {
        return this.stateExists( "coral_fan_direction" ) ? RotationDirection.values()[this.getIntState( "coral_fan_direction" )] : RotationDirection.EAST_WEST;
    }

    public void setCoralColor( CoralColor coralColor ) {
        this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ).toUpperCase() ) : CoralColor.BLUE;
    }

    public enum CoralColor {
        BLUE,
        PINK,
        PURPLE,
        RED,
        YELLOW
    }
}
