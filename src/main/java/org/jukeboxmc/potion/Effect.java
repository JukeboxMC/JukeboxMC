package org.jukeboxmc.potion;

import lombok.ToString;
import org.jukeboxmc.entity.EntityLiving;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public abstract class Effect {

    protected int amplifier;
    protected boolean visible = true;
    protected int duration;

    public abstract int getId();

    public abstract EffectType getEffectType();

    public abstract Color getEffectColor();

    public abstract void apply( EntityLiving entityLiving );

    public boolean canExecute() {
        return false;
    }

    public abstract void update( long currentTick );

    public abstract void remove( EntityLiving entityLiving );

    public static <T extends Effect> T create( EffectType effectType ) {
        try {
            return (T) EffectRegistry.getEffectClass( effectType ).getConstructor().newInstance();
        } catch ( InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
            throw new RuntimeException( e );
        }
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public Effect setAmplifier( int amplifier ) {
        this.amplifier = amplifier;
        return this;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Effect setVisible( boolean visible ) {
        this.visible = visible;
        return this;
    }

    public int getDuration() {
        return this.duration;
    }

    public Effect setDuration( int duration ) {
        this.duration = duration;
        return this;
    }

    public Effect setDuration( int duration, TimeUnit timeUnit ) {
        this.duration = (int) ( timeUnit.toMillis( duration ) / 50 );
        return this;
    }

    public int[] getColor() {
        Color color = this.getEffectColor();
        return new int[]{color.getRed(), color.getGreen(), color.getGreen()};
    }
}
