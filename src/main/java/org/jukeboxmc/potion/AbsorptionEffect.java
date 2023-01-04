package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AbsorptionEffect extends Effect {

    @Override
    public int getId() {
        return 22;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.ABSORPTION;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 36, 107, 251 );
    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        int value = (this.amplifier + 1) * 4;
        if ( value > entityLiving.getAbsorption() ) {
            entityLiving.setAbsorption( value );
        }
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove( EntityLiving entityLiving ) {
        entityLiving.setAbsorption( 0 );
    }
}
