package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockComposter extends Block {

    public BlockComposter() {
        super( "minecraft:composter" );
    }

    public void setComposterFillLevel( int value ) {
        this.setState( "composter_fill_level", value );
    }

    public int getComposterFillLevel() {
        return this.stateExists( "composter_fill_level" ) ? this.getIntState( "composter_fill_level" ) : 0;
    }
}
