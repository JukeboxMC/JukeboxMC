package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WorldLoadEvent extends WorldEvent implements Cancellable {

    private World world;

    public WorldLoadEvent( World world ) {
        super( world );

        this.world = world;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public void setWorld( World world ) {
        this.world = world;
    }
}