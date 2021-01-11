package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSponge extends Block {

    public BlockSponge() {
        super( "minecraft:sponge" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSpongeType().ordinal() );
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
