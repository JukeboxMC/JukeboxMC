package org.jukeboxmc.event.server;

import org.jukeboxmc.Server;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TpsChangeEvent extends ServerEvent {

    private final long lastTps;
    private final long currentTps;

    public TpsChangeEvent( Server server, long lastTps, long currentTps ) {
        super( server );
        this.lastTps = lastTps;
        this.currentTps = currentTps;
    }

    public long getLastTps() {
        return this.lastTps;
    }

    public long getCurrentTps() {
        return this.currentTps;
    }
}
