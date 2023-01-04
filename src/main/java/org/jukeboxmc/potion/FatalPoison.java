package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.event.entity.EntityDamageEvent;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FatalPoison extends Effect {

    @Override
    public int getId() {
        return 25;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.FATAL_POISON;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 78, 147, 49 );
    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        if ( this.canExecute() ) {
            if ( entityLiving.getHealth() > 2 ) {
                entityLiving.damage( new EntityDamageEvent( entityLiving, 1, EntityDamageEvent.DamageSource.MAGIC_EFFECT ) );
            }
        }
    }

    @Override
    public boolean canExecute() {
        int interval;
        if ( ( interval = ( 25 >> this.amplifier ) ) > 0 ) {
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
