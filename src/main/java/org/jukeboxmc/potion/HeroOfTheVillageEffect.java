package org.jukeboxmc.potion;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HeroOfTheVillageEffect extends Effect {

    @Override
    public int getId() {
        return 29;
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return EffectType.HERO_OF_THE_VILLAGE;
    }

    @Override
    public @NotNull Color getEffectColor() {
        return new Color( 68, 255, 68 );
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
