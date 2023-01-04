package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class WeaknessEffect extends Effect {

    @Override
    public int getId() {
        return 18;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.WEAKNESS;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 72, 77, 72 );
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
