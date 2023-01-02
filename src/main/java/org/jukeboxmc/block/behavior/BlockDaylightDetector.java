package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDaylightDetector extends Block {

    public BlockDaylightDetector( Identifier identifier ) {
        super( identifier );
    }

    public BlockDaylightDetector( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
