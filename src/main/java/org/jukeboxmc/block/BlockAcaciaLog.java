package org.jukeboxmc.block;

import org.jukeboxmc.math.Axis;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockAcaciaLog extends Block {

    public BlockAcaciaLog() {
        super( "minecraft:log2" );
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public void setLogType( LogType logType ) {
        this.setState( "new_log_type", logType.name().toLowerCase() );
    }

    public LogType getLogType() {
        return this.stateExists( "new_log_type" ) ? LogType.valueOf( this.getStringState( "new_log_type" ).toUpperCase() ) : LogType.ACACIA;
    }

    public enum LogType {
        ACACIA,
        DARK_OAK
    }
}
