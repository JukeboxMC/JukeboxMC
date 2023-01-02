package org.jukeboxmc.util;

import org.jukeboxmc.logger.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ServerKiller extends Thread {

    private final Logger logger;

    public ServerKiller( Logger logger ) {
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            sleep( TimeUnit.SECONDS.toMillis( 3 ) );
        } catch ( InterruptedException e ) {
            throw new RuntimeException( e );
        }
        System.exit( 1 );
        this.logger.info( "Server shutdown successfully!" );
    }
}
