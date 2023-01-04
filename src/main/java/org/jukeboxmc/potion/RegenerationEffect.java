package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.event.entity.EntityHealEvent;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RegenerationEffect extends Effect {

    @Override
    public int getId() {
        return 10;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.REGENERATION;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 205, 92, 171 );
    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        if ( this.canExecute() ) {
            entityLiving.setHeal( 1, EntityHealEvent.Cause.REGENERATION_EFFECT );
        }
    }

    @Override
    public boolean canExecute() {
        int interval;
        if ( ( interval = ( 40 >> this.amplifier ) ) > 0 ) {
            return ( this.duration % interval ) == 0;
        }
        return false;
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove( EntityLiving entityLiving ) {

    }
}
