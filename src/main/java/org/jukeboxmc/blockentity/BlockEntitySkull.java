package org.jukeboxmc.blockentity;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtMapBuilder;
import org.jukeboxmc.block.Block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySkull extends BlockEntity {

    private byte skullMeta;
    private byte rotation;

    public BlockEntitySkull( Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
    }

    @Override
    public void fromCompound( NbtMap compound ) {
        super.fromCompound( compound );
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
