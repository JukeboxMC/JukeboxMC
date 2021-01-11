package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPrismarine extends Block {

    public BlockPrismarine() {
        super( "minecraft:prismarine" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getPrismarineType().ordinal() );
    }

    public void setPrismarineType( PrismarineType prismarineType ) {
        this.setState( "prismarine_block_type", prismarineType.name().toLowerCase() );
    }

    public PrismarineType getPrismarineType() {
        return this.stateExists( "prismarine_block_type" ) ? PrismarineType.valueOf( this.getStringState( "prismarine_block_type" ).toUpperCase() ) : PrismarineType.DEFAULT;
    }

    public enum PrismarineType {
        DEFAULT,
        DARK,
        BRICKS
    }
}
