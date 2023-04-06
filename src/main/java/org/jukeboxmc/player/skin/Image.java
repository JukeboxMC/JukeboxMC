package org.jukeboxmc.player.skin;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString(exclude = "data")
public class Image {

    private final int width;
    private final int height;
    private final byte[] data;

    public Image( int width, int height, byte[] data ) {
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public byte[] getData() {
        return this.data;
    }

    public static Image getImage( byte[] data ) {
        return switch ( data.length ) {
            case Skin.SINGLE_SKIN_SIZE -> new Image( 64, 32, data );
            case Skin.DOUBLE_SKIN_SIZE -> new Image( 64, 64, data );
            case Skin.SKIN_128_64_SIZE -> new Image( 128, 64, data );
            case Skin.SKIN_128_128_SIZE -> new Image( 128, 128, data );
            default -> null;
        };
    }
}
