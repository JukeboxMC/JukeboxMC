package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.Direction;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTripwireHook extends Block {

    public BlockTripwireHook( Identifier identifier ) {
        super( identifier );
    }

    public BlockTripwireHook( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setPowered( boolean value ) {
        this.setState( "powered_bit", value ? 1 : 0 );
    }

    public boolean isPowered() {
        return this.stateExists( "powered_bit" ) && this.getByteState( "powered_bit" ) == 1;
    }

    public void setAttached( boolean value ) {
        this.setState( "attached_bit", value ? 1 : 0 );
    }

    public boolean isAttached() {
        return this.stateExists( "attached_bit" ) && this.getByteState( "attached_bit" ) == 1;
    }

    public void setDirection( Direction direction ) {
        switch ( direction ) {
            case SOUTH -> this.setState( "direction", 0 );
            case WEST -> this.setState( "direction", 1 );
            case NORTH -> this.setState( "direction", 2 );
            case EAST -> this.setState( "direction", 3 );
        }
    }

    public Direction getDirection() {
        int value = this.stateExists( "direction" ) ? this.getIntState( "direction" ) : 0;
        return switch ( value ) {
            case 0 -> Direction.SOUTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.NORTH;
            default -> Direction.EAST;
        };
    }
}
