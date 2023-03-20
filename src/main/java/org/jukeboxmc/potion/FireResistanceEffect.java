package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FireResistanceEffect extends Effect {

    @Override
    public int getId() {
        return 12;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return EffectType.FIRE_RESISTANCE;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 228, 154, 58 );
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
