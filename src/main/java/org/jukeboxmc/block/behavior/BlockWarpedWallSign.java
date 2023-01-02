package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWarpedWallSign extends BlockWallSign{

    public BlockWarpedWallSign( Identifier identifier ) {
        super( identifier );
    }

    public BlockWarpedWallSign( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }
}
