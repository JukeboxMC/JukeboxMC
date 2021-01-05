package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSponge extends Block {

    public BlockSponge() {
        super( "minecraft:sponge" );
    }

    public void setSpongeType( SpongeType spongeType ) {
        this.setState( "sponge_type", spongeType.name().toLowerCase() );
    }

    public SpongeType getSpongeType() {
        return this.stateExists( "sponge_type" ) ? SpongeType.valueOf( this.getStringState( "sponge_type" ).toUpperCase() ) : SpongeType.DRY;
    }

    public enum SpongeType {
        DRY,
        WET
    }
}
