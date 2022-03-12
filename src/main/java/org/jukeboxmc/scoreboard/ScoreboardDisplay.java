package org.jukeboxmc.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jukeboxmc.entity.Entity;

import java.util.LinkedHashMap;

/**
 * @author GoMint
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ScoreboardDisplay {

    private final Scoreboard scoreboard;
    private final String objectiveName;
    private String displayName;
    private SortOrder sortOrder;

    private LinkedHashMap<Integer, String> lineEntry;

    public DisplayEntry addEntity( Entity entity, int score ) {
        long scoreId = this.scoreboard.addOrUpdateEntity( entity, this.objectiveName, score );
        return new DisplayEntry( this.scoreboard, scoreId );
    }

    public DisplayEntry addLine( String line, int score ) {
        long scoreId = this.scoreboard.addOrUpdateLine( line, this.objectiveName, score );
        this.lineEntry.put( score, line );
        return new DisplayEntry( this.scoreboard, scoreId );
    }

    public void removeEntry( DisplayEntry entry ) {
        this.scoreboard.removeScoreEntry( entry.getScoreId() );
    }

    public String getLine( int score ) {
        return this.lineEntry.getOrDefault( score, null );
    }
}
