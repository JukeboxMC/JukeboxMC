package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySkull extends BlockEntity {

    private byte skullMeta;
    private byte rotation;

    public BlockEntitySkull(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
    }

    @Override
    public void fromCompound(@NotNull NbtMap compound ) {
        super.fromCompound( compound );
        this.skullMeta = compound.getByte( "SkullType" );
        this.rotation = compound.getByte( "Rot" );
    }

    @Override
    public @NotNull NbtMapBuilder toCompound() {
        NbtMapBuilder nbtMapBuilder = super.toCompound();
        nbtMapBuilder.putByte( "SkullType", this.skullMeta );
        nbtMapBuilder.putByte( "Rot", this.rotation );
        return nbtMapBuilder;
    }

    public @NotNull BlockEntitySkull setSkullMeta(byte skullMeta ) {
        this.skullMeta = skullMeta;
        return this;
    }

    public byte getRotation() {
        return this.rotation;
    }

    public @NotNull BlockEntitySkull setRotation(byte rotation ) {
        this.rotation = rotation;
        return this;
    }
}
