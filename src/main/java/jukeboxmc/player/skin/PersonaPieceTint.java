package jukeboxmc.player.skin;

import lombok.ToString;

import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class PersonaPieceTint {

    private final String pieceType;
    private List<String> colors;

    public PersonaPieceTint( String pieceType, List<String> colors ) {
        this.pieceType = pieceType;
        this.colors = colors;
    }

    public String getPieceType() {
        return this.pieceType;
    }

    public List<String> getColors() {
        return this.colors;
    }
}
