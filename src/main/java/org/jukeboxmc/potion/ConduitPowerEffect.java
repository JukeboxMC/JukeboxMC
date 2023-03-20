package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ConduitPowerEffect extends Effect {

    @Override
    public int getId() {
        return 26;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return EffectType.CONDUIT_POWER;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 29, 194, 209 );
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
