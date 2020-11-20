package org.jukeboxmc.network.raknet.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EventHandler {

    private Class<? extends Event> eventClass;

    private List<Consumer<Event>> handlers = new ArrayList<>();

    public EventHandler( Class<? extends Event> eventClass) {
        this.eventClass = eventClass;
    }

    public void handle( Event event ) {
        if ( !this.eventClass.isInstance( event ) ) {
            throw new RuntimeException( "Tried to handle invalid event type!" );
        }
        for ( Consumer<Event> handler : handlers ) {
            handler.accept( event );
        }
    }

    public void addHandler(Consumer<Event> consumer ) {
        this.handlers.add( consumer );
    }

}
