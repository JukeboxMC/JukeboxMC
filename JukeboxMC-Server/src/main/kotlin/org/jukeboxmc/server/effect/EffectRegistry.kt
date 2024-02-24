package org.jukeboxmc.server.effect

import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType

class EffectRegistry {

    companion object {

        private var effectMap: MutableMap<EffectType, Class<out Effect>> = mutableMapOf()

        init {
            register(EffectType.SPEED, SpeedEffect::class.java)
            register(EffectType.SLOWNESS, SlownessEffect::class.java)
            register(EffectType.HASTE, HasteEffect::class.java)
            register(EffectType.MINING_FATIGUE, MiningFatigueEffect::class.java)
            register(EffectType.STRENGTH, StrengthEffect::class.java)
            register(EffectType.INSTANT_HEALTH, InstantHealthEffect::class.java)
            register(EffectType.INSTANT_DAMAGE, InstantDamageEffect::class.java)
            register(EffectType.JUMP_BOOST, JumpBoostEffect::class.java)
            register(EffectType.NAUSEA, NauseaEffect::class.java)
            register(EffectType.REGENERATION, RegenerationEffect::class.java)
            register(EffectType.RESISTANCE, ResistanceEffect::class.java)
            register(EffectType.FIRE_RESISTANCE, FireResistanceEffect::class.java)
            register(EffectType.WATER_BREATHING, WaterBreathingEffect::class.java)
            register(EffectType.INVISIBILITY, InvisibilityEffect::class.java)
            register(EffectType.BLINDNESS, BlindnessEffect::class.java)
            register(EffectType.NIGHT_VISION, NightVisionEffect::class.java)
            register(EffectType.HUNGER, HungerEffect::class.java)
            register(EffectType.WEAKNESS, WeaknessEffect::class.java)
            register(EffectType.POISON, PoisonEffect::class.java)
            register(EffectType.WITHER, WitherEffect::class.java)
            register(EffectType.HEALTH_BOOST, HealthBoostEffect::class.java)
            register(EffectType.ABSORPTION, AbsorptionEffect::class.java)
            register(EffectType.SATURATION, SaturationEffect::class.java)
            register(EffectType.LEVITATION, LevitationEffect::class.java)
            register(EffectType.SLOW_FALLING, SlowFallingEffect::class.java)
            register(EffectType.CONDUIT_POWER, ConduitPowerEffect::class.java)
            register(EffectType.BAD_OMEN, BadOmenEffect::class.java)
            register(EffectType.HERO_OF_THE_VILLAGE, HeroOfTheVillageEffect::class.java)
            register(EffectType.DARKNESS, DarknessEffect::class.java)
            register(EffectType.FATAL_POISON, FatalPoison::class.java)
        }

        private fun register(effectType: EffectType, clazz: Class<out Effect>) {
            this.effectMap[effectType] = clazz
        }

        fun getEffectClass(effectType: EffectType?): Class<out Effect>           {
            return this.effectMap[effectType]!!
        }
    }

}