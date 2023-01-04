package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HungerEffect extends Effect {

    @Override
    public int getId() {
        return 17;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.HUNGER;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 46, 139, 87 );
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
