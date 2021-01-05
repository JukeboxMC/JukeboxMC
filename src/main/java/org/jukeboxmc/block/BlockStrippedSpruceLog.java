package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStrippedSpruceLog extends Block {

    public BlockStrippedSpruceLog() {
        super( "minecraft:stripped_spruce_log" );
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public enum Axis {
        Y,
        X,
        Z
    }
}
