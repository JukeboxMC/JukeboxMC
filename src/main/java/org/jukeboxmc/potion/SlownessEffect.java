package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
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
    public @NotNull EffectType getEffectType() {
        return EffectType.SLOWNESS;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 90, 108, 129 );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void apply(@NotNull EntityLiving entityLiving ) {
        entityLiving.setMovement(entityLiving.getMovement() * (1 - 0.15f * (this.amplifier + 1)));
    }

    @Override
    public void remove(@NotNull EntityLiving entityLiving ) {
        entityLiving.setMovement(entityLiving.getMovement() / (1 - 0.15f * (this.amplifier + 1)));
    }
}
