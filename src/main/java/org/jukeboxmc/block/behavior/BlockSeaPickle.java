package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSeaPickle extends Block {

    public BlockSeaPickle( Identifier identifier ) {
        super( identifier );
    }

    public BlockSeaPickle( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void clusterCount( int value ) { //0-3
        this.setState( "cluster_count", value );
    }

    public int getClusterCount() {
        return this.stateExists( "cluster_count" ) ? this.getIntState( "cluster_count" ) : 0;
    }

    public void setDead( boolean value ) {
        this.setState( "dead_bit", value ? 1 : 0 );
    }

    public boolean isDead() {
        return this.stateExists( "dead_bit" ) && this.getByteState( "dead_bit" ) == 1;
    }
}
