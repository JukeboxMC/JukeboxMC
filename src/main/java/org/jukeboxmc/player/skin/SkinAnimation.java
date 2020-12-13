package org.jukeboxmc.player.skin;

import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class SkinAnimation {

    private final Image image;
    private final int type;
    private final float frames;

    public SkinAnimation( Image image, int type, float frames ) {
        this.image = image;
        this.type = type;
        this.frames = frames;
    }

    public Image getImage() {
        return this.image;
    }

    public int getType() {
        return this.type;
    }

    public float getFrames() {
        return this.frames;
    }
}
