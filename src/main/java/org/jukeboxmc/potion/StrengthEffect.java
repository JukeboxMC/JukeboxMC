package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class StrengthEffect extends Effect {

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.STRENGTH;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 147, 36, 35 );
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
