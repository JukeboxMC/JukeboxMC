package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum LevelEvent {

    SOUND_DOOR( 1003 ),
    PARTICLE_DESTROY(2001 );

    private int id;

    LevelEvent( int id ) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}