package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherWart extends Block {

    public BlockNetherWart( Identifier identifier ) {
        super( identifier );
    }

    public BlockNetherWart( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }

}
