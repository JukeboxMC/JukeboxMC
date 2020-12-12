package org.jukeboxmc.network.raknet.event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RakNetEventManager {

    private Map<Class<? extends RakNetEvent>, RakNetEventHandler> handler = new HashMap<>();

    public void callEvent( RakNetEvent rakNetEvent ) {
        RakNetEventHandler rakNetEventHandler = this.handler.computeIfAbsent( rakNetEvent.getClass(), e -> new RakNetEventHandler( rakNetEvent.getClass() ) );
        rakNetEventHandler.handle( rakNetEvent );
    }

    public <T extends RakNetEvent> void onEvent( Class<? extends RakNetEvent> event, Consumer<T> handler ) {
        RakNetEventHandler rakNetEventHandler = this.handler.computeIfAbsent( event, e -> new RakNetEventHandler( event ) );
        rakNetEventHandler.addHandler( (Consumer<RakNetEvent>) handler );
    }

}
