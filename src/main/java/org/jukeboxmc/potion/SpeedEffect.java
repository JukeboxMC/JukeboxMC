package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
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
    public @NotNull EffectType getEffectType() {
        return EffectType.SPEED;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 124, 175, 198 );
    }

    @Override
    public void update( long currentTick ) {

    }

    @Override
    public void apply(@NotNull EntityLiving entityLiving ) {
        entityLiving.setMovement( ( this.amplifier + 1 ) * 0.2f );
    }

    @Override
    public void remove(@NotNull EntityLiving entityLiving ) {
        entityLiving.getAttribute( AttributeType.MOVEMENT ).reset();
    }
}
