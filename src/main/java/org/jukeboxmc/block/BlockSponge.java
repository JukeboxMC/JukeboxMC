package org.jukeboxmc.block;

import org.jukeboxmc.block.type.SpongeType;
import org.jukeboxmc.item.ItemSponge;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSponge extends Block {

    public BlockSponge() {
        super( "minecraft:sponge" );
    }

    @Override
    public ItemSponge toItem() {
        return new ItemSponge( this.runtimeId );
    }

    @Override
    public BlockType getType() {
        return BlockType.SPONGE;
    }

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.HOE;
    }

    public BlockSponge setSpongeType( SpongeType spongeType ) {
        return this.setState( "sponge_type", spongeType.name().toLowerCase() );
    }

    public SpongeType getSpongeType() {
        return this.stateExists( "sponge_type" ) ? SpongeType.valueOf( this.getStringState( "sponge_type" ) ) : SpongeType.DRY;
    }

}
