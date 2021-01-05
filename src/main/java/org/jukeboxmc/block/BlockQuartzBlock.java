package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockQuartzBlock extends Block {

    public BlockQuartzBlock() {
        super( "minecraft:quartz_block" );
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public void setChiselType( ChiselType chiselType ) {
        this.setState( "chisel_type", chiselType.name().toLowerCase() );
    }

    public ChiselType getChiselType() {
        return this.stateExists( "chisel_type" ) ? ChiselType.valueOf( this.getStringState( "chisel_type" ).toUpperCase() ) : ChiselType.DEFAULT;
    }

    public enum ChiselType {
        DEFAULT,
        CHISELED,
        LINES,
        SMOOTH
    }

    public enum Axis {
        Y,
        X,
        Z
    }
}
