package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSand extends Block {

    public BlockSand() {
        super( "minecraft:sand" );
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
