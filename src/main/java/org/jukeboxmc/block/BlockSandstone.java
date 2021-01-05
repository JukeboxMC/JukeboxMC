package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSandstone extends Block {

    public BlockSandstone() {
        super( "minecraft:sandstone" );
    }

    public void setSandStoneType( SandStoneType sandStoneType ) {
        this.setState( "sand_stone_type", sandStoneType.name().toLowerCase() );
    }

    public SandStoneType getSandStoneType() {
        return this.stateExists( "sand_stone_type" ) ? SandStoneType.valueOf( this.getStringState( "sand_stone_type" ).toUpperCase() ) : SandStoneType.DEFAULT;
    }

    public enum SandStoneType {
        DEFAULT,
        HEIROGLYPHS,
        CUT,
        SMOOTH
    }
}
