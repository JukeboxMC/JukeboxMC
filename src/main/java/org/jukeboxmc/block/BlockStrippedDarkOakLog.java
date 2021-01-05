package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockStrippedDarkOakLog extends Block {

    public BlockStrippedDarkOakLog() {
        super( "minecraft:stripped_dark_oak_log" );
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
