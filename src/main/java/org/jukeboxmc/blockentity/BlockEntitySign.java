package org.jukeboxmc.blockentity;

import com.google.common.base.Joiner;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.nbt.NbtMapBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntitySign extends BlockEntity {

    private List<String> lines;

    public BlockEntitySign( Block block ) {
        super( block );
        this.lines = new ArrayList<>( 4 );
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.putString( "id", "Sign" );
        compound.putString( "Text", Joiner.on( "\n" ).skipNulls().join( this.lines ) );
        return compound;
    }

    public List<String> getLines() {
        return this.lines;
    }
}
