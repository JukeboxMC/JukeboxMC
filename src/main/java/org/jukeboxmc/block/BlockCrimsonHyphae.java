package org.jukeboxmc.block;

import org.jukeboxmc.math.Axis;

public class BlockCrimsonHyphae extends Block {

    public BlockCrimsonHyphae() {
        super("minecraft:crimson_hyphae");
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }
}