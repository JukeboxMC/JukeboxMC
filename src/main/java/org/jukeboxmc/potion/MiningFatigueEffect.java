package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MiningFatigueEffect extends Effect {

    @Override
    public int getId() {
        return 4;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.MINING_FATIGUE;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 74, 66, 23 );
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
