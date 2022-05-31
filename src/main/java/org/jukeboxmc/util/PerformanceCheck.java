package org.jukeboxmc.util;

import org.jukeboxmc.Server;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PerformanceCheck {


    private final String name;
    private long start;
    private int startAt = 5;

    public PerformanceCheck( String name ) {
        this.name = name;
    }

    public PerformanceCheck( String name, int startAt ) {
        this.name = name;
        this.startAt = startAt;
    }

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public void stop() {
        long stop = System.currentTimeMillis();
        long result = stop - this.start;
        if ( result >= this.startAt ) {
            Server.getInstance().getLogger().info( this.name + " -> " + result + "ms Thread: " + Thread.currentThread().getName() );
        }
    }
}
