package org.jukeboxmc.potion;

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
    public EffectType getEffectType() {
        return EffectType.HERO_OF_THE_VILLAGE;
    }

    @Override
    public Color getEffectColor() {
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
