package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WorldLoadEvent extends WorldEvent implements Cancellable {

    private World world;
    private final LoadType loadType;

    /**
     * Creates a new {@link WorldLoadEvent}
     *
     * @param world which should be loaded
     */
    public WorldLoadEvent( World world, LoadType loadType ) {
        super( world );

        this.world = world;
        this.loadType = loadType;
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

    public LoadType getLoadType() {
        return this.loadType;
    }

    public enum LoadType {
        LOAD,
        CREATE
    }
}