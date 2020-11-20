package org.jukeboxmc.network.raknet.event.intern;


import org.jukeboxmc.network.raknet.event.Event;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class TestEvent extends Event {

    private String text;

    public TestEvent( String text ) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
