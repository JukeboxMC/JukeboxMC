package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMapBuilder;
import org.jukeboxmc.block.Block;

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
        return super.toCompound();
    }
}
