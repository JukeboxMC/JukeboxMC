package org.jukeboxmc.event.world;

import org.jukeboxmc.event.CancelableEvent;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LoadWorldEvent extends CancelableEvent {

    private World world;

    public LoadWorldEvent( World world ) {
        this.world = world;
    }

    public World getWorld() {
        return this.world;
    }

    public void setWorld( World world ) {
        this.world = world;
    }
}
