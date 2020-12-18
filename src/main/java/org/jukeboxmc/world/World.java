package org.jukeboxmc.world;

import org.jukeboxmc.player.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class World {

    private String name;

    private Map<Long, Player> players = new HashMap<>();

    public World( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void update( long timestamp ) {
        for ( Player player : this.players.values() ) {
            if ( player != null && player.isSpawned()) {
                player.getPlayerConnection().update( timestamp );
            }
        }
    }

    public void addPlayer( Player player ) {
        this.players.put( player.getEntityId(), player );
    }

    public void removePlayer( Player player ) {
        this.players.remove( player.getEntityId() );
    }

    public Collection<Player> getPlayers() {
        return this.players.values();
    }
}
