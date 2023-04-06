package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockJungleWallSign extends BlockWallSign {

    public BlockJungleWallSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockJungleWallSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
