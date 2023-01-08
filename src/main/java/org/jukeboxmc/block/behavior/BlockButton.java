package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockButton extends Block {

    public BlockButton( Identifier identifier ) {
        super( identifier );
    }

    public BlockButton( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );

        if ( block.isTransparent() ) {
            return false;
        }

        this.setBlockFace( blockFace );
        this.setButtonPressed( false );

        world.setBlock( placePosition, this, 0 );
        return true;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( this.isButtonPressed() ) {
            return false;
        }

        this.setButtonPressed( true );
        this.location.getWorld().playSound( this.location, SoundEvent.POWER_ON );
        this.location.getWorld().scheduleBlockUpdate( this.location, 20 );
        return true;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.SCHEDULED ) {
            this.setButtonPressed( false );
            this.location.getWorld().playSound( this.location, SoundEvent.POWER_OFF );
        }
        return -1;
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
