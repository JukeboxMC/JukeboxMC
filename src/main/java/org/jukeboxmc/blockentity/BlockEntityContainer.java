package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityContainer extends BlockEntity {

    private String customName = null;

    public BlockEntityContainer( Block block ) {
        super( block );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        if ( this.customName != null ) {
            compound.put( "CustomName", this.customName );
        }
        return compound;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName( String customName ) {
        this.customName = customName;
    }
}
