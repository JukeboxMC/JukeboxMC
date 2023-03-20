package org.jukeboxmc.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author GoMint
 * @version 1.0
 */
@RequiredArgsConstructor
public class DisplayEntry {

    /**
     * The api is from the server software GoMint
     */

    private final @NotNull Scoreboard scoreboard;
    @Getter
    private final long scoreId;

    public void setScore( int score ) {
        this.scoreboard.updateScore( this.scoreId, score );
    }

    public int getScore() {
        return this.scoreboard.getScore( this.scoreId );
    }

}
