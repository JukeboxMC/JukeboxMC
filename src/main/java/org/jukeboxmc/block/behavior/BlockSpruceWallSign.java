package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSpruceWallSign extends BlockWallSign {

    public BlockSpruceWallSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockSpruceWallSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
