package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBirchWallSign extends BlockWallSign{

    public BlockBirchWallSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockBirchWallSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
