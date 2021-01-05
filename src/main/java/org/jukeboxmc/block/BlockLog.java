package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLog extends Block {

    public BlockLog() {
        super( "minecraft:log" );
    }

    public void setLogType( LogType logType ) {
        this.setState( "old_log_type", logType.name().toLowerCase() );
    }

    public LogType getLogType() {
        return this.stateExists( "old_log_type" ) ? LogType.valueOf( this.getStringState( "old_log_type" ).toUpperCase() ) : LogType.OAK;
    }

    public void setAxis( Axis axis ) {
        this.setState( "pillar_axis", axis.name().toLowerCase() );
    }

    public Axis getAxis() {
        return this.stateExists( "pillar_axis" ) ? Axis.valueOf( this.getStringState( "pillar_axis" ).toUpperCase() ) : Axis.Y;
    }

    public enum LogType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE
    }

    public enum Axis {
        Y,
        X,
        Z
    }
}
