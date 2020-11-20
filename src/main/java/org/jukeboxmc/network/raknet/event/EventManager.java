package org.jukeboxmc.network.raknet.event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EventManager {

    private Map<Class<? extends Event>, EventHandler> handler = new HashMap<>();

    public void callEvent( Event event ) {
        EventHandler eventHandler = this.handler.computeIfAbsent( event.getClass(), e -> new EventHandler( event.getClass() ) );
        eventHandler.handle( event );
    }

    public <T extends Event> void onEvent( Class<? extends Event> event, Consumer<T> handler ) {
        EventHandler eventHandler = this.handler.computeIfAbsent( event, e -> new EventHandler( event ) );
        eventHandler.addHandler( (Consumer<Event>) handler );
    }

}
