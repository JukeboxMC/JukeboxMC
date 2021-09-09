package jukeboxmc.player.skin;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class PersonaPiece {

    private final String pieceId;
    private final String pieceType;
    private final String packId;
    private final String productId;
    private final boolean isDefault;

    public PersonaPiece( String pieceId, String pieceType, String packId, String productId, boolean isDefault ) {
        this.pieceId = pieceId;
        this.pieceType = pieceType;
        this.packId = packId;
        this.productId = productId;
        this.isDefault = isDefault;
    }

    public String getPieceId() {
        return this.pieceId;
    }

    public String getPieceType() {
        return this.pieceType;
    }

    public String getPackId() {
        return this.packId;
    }

    public String getProductId() {
        return this.productId;
    }

    public boolean isDefault() {
        return this.isDefault;
    }
}
