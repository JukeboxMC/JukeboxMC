package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAir extends Block implements Waterlogable {

    public BlockAir( Identifier identifier ) {
        super( identifier );
    }

    public BlockAir( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean canBeReplaced( Block block ) {
        return true;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }
}
