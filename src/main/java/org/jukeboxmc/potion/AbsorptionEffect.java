package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
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
    public @NotNull EffectType getEffectType() {
        return EffectType.ABSORPTION;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 36, 107, 251 );
    }

    @Override
    public void apply(@NotNull EntityLiving entityLiving ) {
        int value = (this.amplifier + 1) * 4;
        if ( value > entityLiving.getAbsorption() ) {
            entityLiving.setAbsorption( value );
        }
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void remove(@NotNull EntityLiving entityLiving ) {
        entityLiving.setAbsorption( 0 );
    }
}
