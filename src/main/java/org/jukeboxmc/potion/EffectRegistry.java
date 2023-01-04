package org.jukeboxmc.potion;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EffectRegistry {

    private static final Map<EffectType, Class<? extends Effect>> EFFECT_MAP = new HashMap<>();

    public static void init() {
        register( EffectType.SPEED, SpeedEffect.class );
        register( EffectType.SLOWNESS, SlownessEffect.class );
        register( EffectType.HASTE, HasteEffect.class );
        register( EffectType.MINING_FATIGUE, MiningFatigueEffect.class );
        register( EffectType.STRENGTH, StrengthEffect.class );
        register( EffectType.INSTANT_HEALTH, InstantHealthEffect.class );
        register( EffectType.INSTANT_DAMAGE, InstantDamageEffect.class );
        register( EffectType.JUMP_BOOST, JumpBoostEffect.class );
        register( EffectType.NAUSEA, NauseaEffect.class );
        register( EffectType.REGENERATION, RegenerationEffect.class );
        register( EffectType.RESISTANCE, ResistanceEffect.class );
        register( EffectType.FIRE_RESISTANCE, FireResistanceEffect.class );
        register( EffectType.WATER_BREATHING, WaterBreathingEffect.class );
        register( EffectType.INVISIBILITY, InvisibilityEffect.class );
        register( EffectType.BLINDNESS, BlindnessEffect.class );
        register( EffectType.NIGHT_VISION, NightVisionEffect.class );
        register( EffectType.HUNGER, HungerEffect.class );
        register( EffectType.WEAKNESS, WeaknessEffect.class );
        register( EffectType.POISON, PoisonEffect.class );
        register( EffectType.WITHER, WitherEffect.class );
        register( EffectType.HEALTH_BOOST, HealthBoostEffect.class );
        register( EffectType.ABSORPTION, AbsorptionEffect.class );
        register( EffectType.SATURATION, SaturationEffect.class );
        register( EffectType.LEVITATION, LevitationEffect.class );
        register( EffectType.SLOW_FALLING, SlowFallingEffect.class );
        register( EffectType.CONDUIT_POWER, ConduitPowerEffect.class );
        register( EffectType.BAD_OMEN, BadOmenEffect.class );
        register( EffectType.HERO_OF_THE_VILLAGE, HeroOfTheVillageEffect.class );
        register( EffectType.DARKNESS, DarknessEffect.class );
        register( EffectType.FATAL_POISON, FatalPoison.class );
    }

    private static void register( EffectType effectType, Class<? extends Effect> clazz ) {
        EFFECT_MAP.put( effectType, clazz );
    }

    public static Class<? extends Effect> getEffectClass( EffectType effectType ) {
        return EFFECT_MAP.get( effectType );
    }

}
