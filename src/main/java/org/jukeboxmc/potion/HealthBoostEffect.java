package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HealthBoostEffect extends Effect {

    @Override
    public int getId() {
        return 21;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.HEALTH_BOOST;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 248, 125, 35 );
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
