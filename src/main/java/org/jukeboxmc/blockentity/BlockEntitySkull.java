package org.jukeboxmc.blockentity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class BlockEntitySkull extends BlockEntity {

    private byte skullMeta;
    private byte rotation;

    public BlockEntitySkull( Block block ) {
        super( block );
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
        this.skullMeta = compound.getByte( "SkullType" );
        this.rotation = compound.getByte( "Rot" );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder nbtMapBuilder = super.toCompound();
        nbtMapBuilder.putByte( "SkullType", this.skullMeta );
        nbtMapBuilder.putByte( "Rot", this.rotation );
        return nbtMapBuilder;
    }

    public BlockEntitySkull setSkullMeta( byte skullMeta ) {
        this.skullMeta = skullMeta;
        return this;
    }

    public byte getRotation() {
        return this.rotation;
    }

    public BlockEntitySkull setRotation( byte rotation ) {
        this.rotation = rotation;
        return this;
    }
}
