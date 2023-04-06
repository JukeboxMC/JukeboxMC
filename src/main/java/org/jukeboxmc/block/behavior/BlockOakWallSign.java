package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockOakWallSign extends BlockWallSign {

    public BlockOakWallSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockOakWallSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
