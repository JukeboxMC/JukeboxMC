package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WorldUnloadEvent extends WorldEvent implements Cancellable {

    private World world;

    /**
     * Creates a new {@link WorldUnloadEvent}
     *
     * @param world which should be unloaded
     */
    public WorldUnloadEvent( World world ) {
        super( world );

        this.world = world;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    /**
     * Modifies the {@link World}
     *
     * @param world which should be modified
     */
    public void setWorld( World world ) {
        this.world = world;
    }
}