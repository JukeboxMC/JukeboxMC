package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockConcretepowder extends Block {

    public BlockConcretepowder() {
        super( "minecraft:concretepowder" );
    }

    public void setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ).toUpperCase() ) : BlockColor.WHITE;
    }
}
