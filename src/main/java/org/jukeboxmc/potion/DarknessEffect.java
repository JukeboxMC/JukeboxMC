package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DarknessEffect extends Effect {

    @Override
    public int getId() {
        return 30;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.DARKNESS;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 41, 39, 33 );
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
