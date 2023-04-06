package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.SpongeType;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSponge extends Block {

    public BlockSponge( Identifier identifier ) {
        super( identifier );
    }

    public BlockSponge( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    public BlockSponge setSpongeType( SpongeType spongeType ) {
        return this.setState( "sponge_type", spongeType.name().toLowerCase() );
    }

    public SpongeType getSpongeType() {
        return this.stateExists( "sponge_type" ) ? SpongeType.valueOf( this.getStringState( "sponge_type" ) ) : SpongeType.DRY;
    }
}
