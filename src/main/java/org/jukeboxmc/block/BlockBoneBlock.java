package org.jukeboxmc.block;

import org.jukeboxmc.math.Axis;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBoneBlock extends Block {

    public BlockBoneBlock() {
        super( "minecraft:bone_block" );
    }

    public void setDeprecated( int value ) { //0-3 Idk what it is
        this.setState( "deprecated", value );
    }

    public int getDeprecated() {
        return this.stateExists( "deprecated" ) ? this.getIntState( "deprecated" ) : 0;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }
}
