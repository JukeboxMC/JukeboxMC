package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.LeverDirection;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLever extends Block {

    public BlockLever( Identifier identifier ) {
        super( identifier );
    }

    public BlockLever( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setOpen( boolean value ) {
        this.setState( "open_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isOpen() {
        return this.stateExists( "open_bit" ) && this.getByteState( "open_bit" ) == 1;
    }

    public void setLeverDirection( LeverDirection leverDirection ) {
        this.setState( "lever_direction", leverDirection.name().toLowerCase() );
    }

    public LeverDirection getLeverDirection() {
        return this.stateExists( "lever_direction" ) ? LeverDirection.valueOf( this.getStringState( "lever_direction" ) ) : LeverDirection.DOWN_EAST_WEST;
    }
}
