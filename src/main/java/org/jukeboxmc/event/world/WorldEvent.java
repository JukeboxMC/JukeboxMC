package org.jukeboxmc.event.world;

import org.jukeboxmc.event.Event;
import org.jukeboxmc.world.World;

/**
 * @author Kaooot
 * @version 1.0
 */
public abstract class WorldEvent extends Event {

    private World world;

    /**
     * Creates a new {@link WorldEvent}
     *
     * @param world which represents the world which comes with this event
     */
    public WorldEvent( World world ) {
        this.world = world;
    }

    /**
     * Retrives the {@link World} which comes with this {@link WorldEvent}
     *
     * @return a fresh {@link World}
     */
    public World getWorld() {
        return this.world;
    }
}