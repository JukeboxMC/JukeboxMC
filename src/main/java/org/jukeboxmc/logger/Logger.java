package org.jukeboxmc.logger;

import lombok.extern.log4j.Log4j2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Log4j2
public class Logger {

    private static final Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    public void info( String message ) {
        log.info( message );
    }

    public void info( Object message ) {
        log.info( message );
    }

    public void warn( Object message ) {
        log.warn( message );
    }

    public void error( Object message ) {
        log.error( message );
    }

    public void error( String message, Throwable throwable ) {
        log.error( message, throwable );
    }

    public void debug( Object message ) {
        log.debug( message );
    }
}
