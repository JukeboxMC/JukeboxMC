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
    private final int expression;

    public SkinAnimation( Image image, int type, float frames, int expression ) {
        this.image = image;
        this.type = type;
        this.frames = frames;
        this.expression = expression;
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

    public int getExpression() {
        return this.expression;
    }
}
