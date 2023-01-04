package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BadOmenEffect extends Effect {

    @Override
    public int getId() {
        return 28;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.BAD_OMEN;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 11, 97, 56 );
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
