package org.jukeboxmc.blockentity;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.type.BlockColor;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtMapBuilder;
import org.jukeboxmc.nbt.NbtType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityBanner extends BlockEntity {

    private int baseColor;
    private int type;

    private Map<String, Integer> patterns = new LinkedHashMap<>();

    public BlockEntityBanner( Block block ) {
        super( block );
    }

    @Override
    public void setCompound( NbtMap compound ) {
        super.setCompound( compound );
        this.baseColor = compound.getInt( "Base", 0 );
        this.type = compound.getInt( "Type", 0 );

        List<NbtMap> patterns = compound.getList( "blocks", NbtType.COMPOUND );
        if ( patterns != null ) {
            for ( NbtMap pattern : patterns ) {
                this.patterns.put( pattern.getString( "Pattern", "ss" ), pattern.getInt( "Color", 0 ) );
            }
        }
    }

    @Override
    public NbtMapBuilder toCompound() {
        NbtMapBuilder compound = super.toCompound();
        compound.putInt( "Base", this.baseColor );
        compound.putInt( "Type", this.type );

        if ( this.patterns.size() > 0 ) {
            List<NbtMap> pattern = new ArrayList<>();
            for ( Map.Entry<String, Integer> entry : this.patterns.entrySet() ) {
                NbtMapBuilder patternBuilder = NbtMap.builder();
                patternBuilder.putString( "Pattern", entry.getKey() );
                patternBuilder.putInt( "Color", entry.getValue() );
                pattern.add( patternBuilder.build() );
            }
            compound.putList( "Patterns", NbtType.COMPOUND, pattern );
        }
        return compound;
    }

    public BlockEntityBanner setColor( BlockColor blockColor ) {
        this.baseColor = blockColor.ordinal();
        return this;
    }

    public BlockColor getBaseColor() {
        return BlockColor.values()[this.baseColor];
    }

    public BlockEntityBanner setType( int type ) {
        this.type = type;
        return this;
    }

    public int getType() {
        return this.type;
    }
}
