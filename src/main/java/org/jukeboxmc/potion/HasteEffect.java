package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HasteEffect extends Effect {

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return EffectType.HASTE;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 217, 192, 67 );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void apply( EntityLiving entityLiving ) {

    }

    @Override
    public void remove( EntityLiving entityLiving ) {

    }
}
