package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockSign extends Block {

    public BlockSign( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        return (BlockEntitySign) this.world.getBlockEntity( this.getLocation(), this.location.getDimension() );
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public List<String> getLines() {
        BlockEntitySign blockEntity = this.getBlockEntity();
        return blockEntity != null ? new ArrayList<>( blockEntity.getLines() ) : null;
    }

    public void setLine( int line, String value ) {
        if ( line > 4 || line < 0 ) {
            return;
        }

        BlockEntitySign blockEntity = this.getBlockEntity();
        if ( blockEntity != null ) {
            blockEntity.getLines().set( line, value );
            blockEntity.updateBlockEntitySign();
        }
    }

    public void setSignDirection( SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }

}
