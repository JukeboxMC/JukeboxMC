package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import org.jetbrains.annotations.NotNull;
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

    public BlockSponge setSpongeType(@NotNull SpongeType spongeType ) {
        return this.setState( "sponge_type", spongeType.name().toLowerCase() );
    }

    public @NotNull SpongeType getSpongeType() {
        return this.stateExists( "sponge_type" ) ? SpongeType.valueOf( this.getStringState( "sponge_type" ) ) : SpongeType.DRY;
    }
}
