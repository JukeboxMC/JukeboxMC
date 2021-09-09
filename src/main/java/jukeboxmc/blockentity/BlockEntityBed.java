package jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBed extends BlockEntityContainer {

    private byte color = 14;

    public BlockEntityBed( Block block ) {
        super( block );
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
        this.color = compound.getByte( "color" );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.putByte( "color", this.color );
        return compound;
    }

    public BlockEntityBed setColor( BlockColor blockColor ) {
        this.color = (byte) blockColor.ordinal();
        return this;
    }

    public BlockColor getColor() {
        return BlockColor.values()[this.color];
    }
}
