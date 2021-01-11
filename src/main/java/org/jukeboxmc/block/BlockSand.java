package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSand extends Block {

    public BlockSand() {
        super( "minecraft:sand" );
    }

    @Override
    public Item toItem() {
        return super.toItem().setMeta( this.getSandType().ordinal() );
    }

    public void setSandType( SandType sandType ) {
        this.setState( "sand_type", sandType.name().toLowerCase() );
    }

    public SandType getSandType() {
        return this.stateExists( "sand_type" ) ? SandType.valueOf( this.getStringState( "sand_type" ).toUpperCase() ) : SandType.NORMAL;
    }

    public enum SandType {
        NORMAL,
        RED
    }
}
