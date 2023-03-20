package org.jukeboxmc.blockentity;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.nbt.NbtMapBuilder;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.BlockColor;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBed extends BlockEntity {

    private byte color = 14;

    public BlockEntityBed(@NotNull Block block, BlockEntityType blockEntityType ) {
        super( block, blockEntityType );
    }

    @Override
    public void fromCompound(@NotNull NbtMap compound ) {
        super.fromCompound( compound );
        this.color = compound.getByte( "color" );
    }

    @Override
    public @NotNull NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.putByte( "color", this.color );
        return compound;
    }

    public @NotNull BlockEntityBed setColor(@NotNull BlockColor blockColor ) {
        this.color = (byte) blockColor.ordinal();
        return this;
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.color];
    }
}
