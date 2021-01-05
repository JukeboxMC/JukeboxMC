package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedSandstoneStairs extends Block {

    public BlockRedSandstoneStairs() {
        super( "minecraft:red_sandstone_stairs" );
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
