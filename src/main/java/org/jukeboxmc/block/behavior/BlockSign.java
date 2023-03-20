package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.SignDirection;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSign extends Block {

    public BlockSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public BlockEntitySign getBlockEntity() {
        return (BlockEntitySign) this.location.getWorld().getBlockEntity( this.location, this.location.getDimension() );
    }

    public @Nullable List<String> getLines() {
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

    public void setSignDirection(@NotNull SignDirection signDirection ) {
        this.setState( "ground_sign_direction", signDirection.ordinal() );
    }

    public SignDirection getSignDirection() {
        return this.stateExists( "ground_sign_direction" ) ? SignDirection.values()[this.getIntState( "ground_sign_direction" )] : SignDirection.SOUTH;
    }
}
