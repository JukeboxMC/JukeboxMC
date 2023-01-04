package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.attribute.AttributeType;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SpeedEffect extends Effect {

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.SPEED;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 124, 175, 198 );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void apply( EntityLiving entityLiving ) {
        entityLiving.setMovement( ( this.amplifier + 1 ) * 0.2f );
    }

    @Override
    public void remove( EntityLiving entityLiving ) {
        entityLiving.getAttribute( AttributeType.MOVEMENT ).reset();
    }
}
