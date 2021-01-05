package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStainedGlass extends Block {

    public BlockStainedGlass() {
        super( "minecraft:stained_glass" );
    }

    public void setColor( BlockColor color ) {
        this.setState( "color", color.name().toLowerCase() );
    }

    public BlockColor getColor() {
        return this.stateExists( "color" ) ? BlockColor.valueOf( this.getStringState( "color" ).toUpperCase() ) : BlockColor.WHITE;
    }
}
