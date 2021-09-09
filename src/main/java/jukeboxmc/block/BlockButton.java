package jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockButton extends BlockWaterlogable {

    public BlockButton( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );
        Block placedBlock = world.getBlock( placePosition );

        if ( block.isTransparent() ) {
            return false;
        }

        this.setBlockFace( blockFace );
        this.setButtonPressed( false );

        world.setBlock( placePosition, this );
        world.setBlock( placePosition, placedBlock, 1 );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( this.isButtonPressed() ) {
            return false;
        }

        this.setButtonPressed( true );
        this.world.playSound( this.location, LevelSound.POWER_ON );
        this.world.scheduleBlockUpdate( this.location, 20 );
        return true;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.SCHEDULED ) {
            this.setButtonPressed( false );
            this.world.playSound( this.location, LevelSound.POWER_OFF );
        }
        return -1;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setButtonPressed( boolean value ) {
        this.setState( "button_pressed_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isButtonPressed() {
        return this.stateExists( "button_pressed_bit" ) && this.getByteState( "button_pressed_bit" ) == 1;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }

}
