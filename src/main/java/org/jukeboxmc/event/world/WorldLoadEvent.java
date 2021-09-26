package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WorldLoadEvent extends WorldEvent implements Cancellable {

    private World world;
    private boolean prepareWorld;
    private final LoadType loadType;

    /**
     * Creates a new {@link WorldLoadEvent}
     *
     * @param world which should be loaded
     */
    public WorldLoadEvent( World world, boolean prepareWorld, LoadType loadType ) {
        super( world );

        this.world = world;
        this.prepareWorld = prepareWorld;
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

    public boolean isPrepareWorld() {
        return this.prepareWorld;
    }

    public void setPrepareWorld( boolean prepareWorld ) {
        this.prepareWorld = prepareWorld;
    }

    public LoadType getLoadType() {
        return this.loadType;
    }

    public enum LoadType {
        LOAD,
        CREATE
    }
}