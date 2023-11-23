package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTerracotta extends Block {

    public BlockTerracotta(Identifier identifier ) {
        super( identifier );
    }

    public BlockTerracotta(Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
