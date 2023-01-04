package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SlownessEffect extends Effect {

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.SLOWNESS;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 90, 108, 129 );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        entityLiving.setMovement(entityLiving.getMovement() * (1 - 0.15f * (this.amplifier + 1)));
    }

    @Override
    public void remove( EntityLiving entityLiving ) {
        entityLiving.setMovement(entityLiving.getMovement() / (1 - 0.15f * (this.amplifier + 1)));
    }
}
