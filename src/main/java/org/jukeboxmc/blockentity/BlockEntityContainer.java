package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityContainer extends BlockEntity {

    protected String customName = "";

    public BlockEntityContainer( Block block ) {
        super( block );
    }

    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );
        this.customName = compound.getString( "CustomName" );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.put( "CustomName", this.customName );
        return compound;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName( String customName ) {
        this.customName = customName;
    }
}
