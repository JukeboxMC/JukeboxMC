package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.CancelableBlockEvent;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SignChangeEvent extends CancelableBlockEvent {

    private Player player;
    private List<String> lines;

    public SignChangeEvent( Block block, Player player, List<String> lines ) {
        super( block );
        this.player = player;
        this.lines = lines;
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void setLines( List<String> lines ) {
        this.lines = lines;
    }

    public void setLine( int line, String text ) {
        if ( line > 4 || line < 1 ) {
            return;
        }

        if ( this.lines.size() < line ) {
            for ( int i = 0; i < line - this.lines.size(); i++ ) {
                this.lines.add( "" );
            }
        }

        this.lines.set( line - 1, text );
    }

    public Player getPlayer() {
        return this.player;
    }
}