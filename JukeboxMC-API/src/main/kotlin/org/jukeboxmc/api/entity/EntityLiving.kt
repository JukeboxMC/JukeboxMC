package org.jukeboxmc.api.entity

import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.event.entity.EntityHealEvent

/**
 * @author Kaooot
 * @version 1.0
 */
interface EntityLiving : Entity {

    fun getHealth(): Float

    fun setHealth(value: Float)

    fun getMaxHealth(): Float

    fun setMaxHealth(value: Float)

    fun getAbsorption(): Float

    fun setAbsorption(value: Float)

    fun getAttackDamage(): Float

    fun setAttackDamage(value: Float)

    fun getMovement(): Float

    fun setMovement(value: Float)

    fun heal(value: Float)

    fun getInAirTicks(): Long

    fun setInAirTicks(value: Long)

    fun getFallingDistance(): Float

    fun setFallingDistance(value: Float)

    fun getHighestPosition(): Float

    fun setHighestPosition(value: Float)

    fun heal(value: Int, cause: EntityHealEvent.Cause)

    fun addEffect(effect: Effect)

    fun removeEffect(effectType: EffectType)

    fun removeAllEffects()

    fun getEffect(effectType: EffectType): Effect?

    fun hasEffect(effectType: EffectType): Boolean

    fun kill()
}