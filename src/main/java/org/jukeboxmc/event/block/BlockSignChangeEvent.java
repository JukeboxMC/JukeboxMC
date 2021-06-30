package org.jukeboxmc.event.block;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.player.Player;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSignChangeEvent extends BlockEvent implements Cancellable {

    private Player player;
    private List<String> lines;

    /**
     * Creates a new {@link BlockSignChangeEvent}
     *
     * @param block  which represents the sign block
     * @param player who changed the content of the sign
     * @param lines  which is the text content of the sign
     */
    public BlockSignChangeEvent( Block block, Player player, List<String> lines ) {
        super( block );

        this.player = player;
        this.lines = lines;
    }

    /**
     * Retrieves the player who changed the content of the sign
     *
     * @return a fresh {@link Player}
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Retrieves all lines of the sign
     *
     * @return a fresh {@link List<String>}
     */
    public List<String> getLines() {
        return this.lines;
    }

    /**
     * Modifies lines of the sign
     *
     * @param lines which should be modified
     */
    public void setLines( List<String> lines ) {
        this.lines = lines;
    }

    /**
     * Modifies the given line of the sign
     *
     * @param line which should be updated
     * @param text which represents the new content
     */
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
}