package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBeehive extends BlockEntityContainer {

    public BlockEntityBeehive( Block block ) {
        super( block );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        return compound;
    }
}
