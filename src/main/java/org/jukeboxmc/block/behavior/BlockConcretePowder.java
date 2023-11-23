package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConcretePowder extends Block {

    public BlockConcretePowder( Identifier identifier ) {
        super( identifier );
    }

    public BlockConcretePowder( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
