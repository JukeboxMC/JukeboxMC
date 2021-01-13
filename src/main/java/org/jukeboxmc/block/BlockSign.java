package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSign extends Block {

    public BlockSign( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        BlockEntitySign blockEntitySign = (BlockEntitySign) this.world.getBlockEntity( this.position );
        if ( blockEntitySign == null ) {
            return new BlockEntitySign( this );
        }
        return blockEntitySign;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public Block getWallSignBlock() {
        return null;
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
