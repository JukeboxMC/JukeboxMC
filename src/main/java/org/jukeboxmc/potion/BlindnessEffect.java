package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlindnessEffect extends Effect {

    @Override
    public int getId() {
        return 15;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.BLINDNESS;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 191, 192, 192 );
    }

    @Override
    public void apply( EntityLiving entityLiving ) {

    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove( EntityLiving entityLiving ) {

    }
}
