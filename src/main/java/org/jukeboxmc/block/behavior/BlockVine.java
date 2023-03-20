package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
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
public class BlockVine extends Block {

    public BlockVine( Identifier identifier ) {
        super( identifier );
    }

    public BlockVine( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock(@NotNull Player player, @NotNull World world, Vector blockPosition, @NotNull Vector placePosition, Vector clickedPosition, Item itemInHand, @NotNull BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );
        if ( block.isSolid() && blockFace.getHorizontalIndex() != -1 ) {
            this.setVineDirection( 1 << blockFace.opposite().getHorizontalIndex() );
            world.setBlock( placePosition, this );
            return true;
        }
        return false;
    }

    public @NotNull BlockVine setVineDirection(@NotNull BlockFace blockFace ) {
        this.setVineDirection( 1 << blockFace.opposite().getHorizontalIndex() );
        return this;
    }

    public @NotNull BlockVine setVineDirection(int value ) { //0-15 TODO -> Add Direction Class
        this.setState( "vine_direction_bits", value );
        return this;
    }

    public int getVineDirection() {
        return this.stateExists( "vine_direction_bits" ) ? this.getIntState( "vine_direction_bits" ) : 0;
    }
}
