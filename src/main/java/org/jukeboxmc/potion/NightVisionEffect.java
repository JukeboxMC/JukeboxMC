package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class NightVisionEffect extends Effect {

    @Override
    public int getId() {
        return 16;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.NIGHT_VISION;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 0, 0, 139 );
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
