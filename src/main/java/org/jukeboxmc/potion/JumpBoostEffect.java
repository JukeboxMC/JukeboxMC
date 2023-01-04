package org.jukeboxmc.potion;

import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class JumpBoostEffect extends Effect {

    @Override
    public int getId() {
        return 8;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.JUMP_BOOST;
    }

    @Override
    public Color getEffectColor() {
        return new Color( 34, 255, 76 );
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
