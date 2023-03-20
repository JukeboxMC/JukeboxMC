package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.CrossDirection;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStairs extends Block {

    public BlockStairs( Identifier identifier ) {
        super( identifier );
    }

    public BlockStairs( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, @NotNull Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        this.setCrossDirection( player.getDirection().toCrossDirection() );

        if ( ( clickedPosition.getY() > 0.5 && blockFace != BlockFace.UP ) || blockFace == BlockFace.DOWN ) {
            this.setUpsideDown( true );
        }

        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
    }

    public void setUpsideDown( boolean value ) {
        this.setState( "upside_down_bit", value ? (byte) 1 : (byte) 0 );
    }

    public boolean isUpsideDown() {
        return this.stateExists( "upside_down_bit" ) && this.getByteState( "upside_down_bit" ) == 1;
    }

    public void setCrossDirection(@NotNull CrossDirection crossDirection ) {
        switch ( crossDirection ) {
            case EAST -> this.setState( "weirdo_direction", 0 );
            case WEST -> this.setState( "weirdo_direction", 1 );
            case SOUTH -> this.setState( "weirdo_direction", 2 );
            default -> this.setState( "weirdo_direction", 3 );
        }
    }

    public @NotNull CrossDirection getCrossDirection() {
        int value = this.stateExists( "weirdo_direction" ) ? this.getIntState( "weirdo_direction" ) : 0;
        return switch ( value ) {
            case 0 -> CrossDirection.EAST;
            case 1 -> CrossDirection.WEST;
            case 2 -> CrossDirection.SOUTH;
            default -> CrossDirection.NORTH;
        };
    }
}
