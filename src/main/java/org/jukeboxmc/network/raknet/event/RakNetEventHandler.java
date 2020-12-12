package org.jukeboxmc.network.raknet.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RakNetEventHandler {

    private Class<? extends RakNetEvent> eventClass;

    private List<Consumer<RakNetEvent>> handlers = new ArrayList<>();

    public RakNetEventHandler( Class<? extends RakNetEvent> eventClass ) {
        this.eventClass = eventClass;
    }

    public void handle( RakNetEvent rakNetEvent ) {
        if ( !this.eventClass.isInstance( rakNetEvent ) ) {
            throw new RuntimeException( "Tried to handle invalid event type!" );
        }
        for ( Consumer<RakNetEvent> handler : handlers ) {
            handler.accept( rakNetEvent );
        }
    }

    public void addHandler( Consumer<RakNetEvent> consumer ) {
        this.handlers.add( consumer );
    }

}
