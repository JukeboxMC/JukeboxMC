package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jukeboxmc.block.data.StoneSlabType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDoubleStoneSlab extends BlockSlab {

    public BlockDoubleStoneSlab( Identifier identifier ) {
        super( identifier );
    }

    public BlockDoubleStoneSlab( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public BlockDoubleStoneSlab setStoneSlabType( StoneSlabType stoneSlabType ) {
        return this.setState( "stone_slab_type", stoneSlabType.name().toLowerCase() );
    }

    public StoneSlabType getStoneSlabType() {
        return this.stateExists( "stone_slab_type" ) ? StoneSlabType.valueOf( this.getStringState( "stone_slab_type" ) ) : StoneSlabType.SMOOTH_STONE;
    }
}
