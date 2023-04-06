package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockComposter extends Block {

    public BlockComposter( Identifier identifier ) {
        super( identifier );
    }

    public BlockComposter( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setComposterFillLevel( int value ) {
        this.setState( "composter_fill_level", value );
    }

    public int getComposterFillLevel() {
        return this.stateExists( "composter_fill_level" ) ? this.getIntState( "composter_fill_level" ) : 0;
    }
}
