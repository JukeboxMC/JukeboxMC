package jukeboxmc.block;

import org.jukeboxmc.block.direction.RotationDirection;
import org.jukeboxmc.block.type.CoralColor;
import org.jukeboxmc.item.ItemCoralFanDead;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCoralFanDead extends Block {

    public BlockCoralFanDead() {
        super( "minecraft:coral_fan_dead" );
    }

    @Override
    public ItemCoralFanDead toItem() {
        return new ItemCoralFanDead( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CORAL_FAN_DEAD;
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

    public BlockCoralFanDead setCoralColor( CoralColor coralColor ) {
        return this.setState( "coral_color", coralColor.name().toLowerCase() );
    }

    public CoralColor getCoralColor() {
        return this.stateExists( "coral_color" ) ? CoralColor.valueOf( this.getStringState( "coral_color" ) ) : CoralColor.BLUE;
    }
}
