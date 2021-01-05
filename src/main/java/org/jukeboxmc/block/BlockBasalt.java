package org.jukeboxmc.block;

import org.jukeboxmc.math.Axis;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBasalt extends Block {

    public BlockBasalt() {
        super( "minecraft:basalt" );
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }
}
