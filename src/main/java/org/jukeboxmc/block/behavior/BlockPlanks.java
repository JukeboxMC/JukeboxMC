package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPlanks extends Block {

    public BlockPlanks( Identifier identifier ) {
        super( identifier );
    }

    public BlockPlanks( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
