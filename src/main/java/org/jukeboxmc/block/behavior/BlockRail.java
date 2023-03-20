package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.direction.RailDirection;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRail extends Block {

    public BlockRail( Identifier identifier ) {
        super( identifier );
    }

    public BlockRail( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public @NotNull BlockRail setRailDirection(@NotNull RailDirection railDirection ) {
        this.setState( "rail_direction", railDirection.ordinal() );
        return this;
    }

    public RailDirection getRailDirection() {
        return this.stateExists( "rail_direction" ) ? RailDirection.values()[this.getIntState( "rail_direction" )] : RailDirection.NORTH_SOUTH;
    }
}
