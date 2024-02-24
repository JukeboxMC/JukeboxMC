package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Cancellable

open class EntityDamageEvent(
    entity: Entity,
    private var damage: Float,
    private val damageSource: DamageSource) : EntityEvent(entity), Cancellable {

    private var finalDamage: Float = 0F

    fun getDamage(): Float {
        return this.damage
    }

    fun setDamage(damage: Float) {
        this.damage = damage
    }

    fun getFinalDamage(): Float {
        return this.finalDamage
    }

    fun setFinalDamage(finalDamage: Float) {
        this.finalDamage = finalDamage
    }

    fun getDamageSource(): DamageSource {
        return this.damageSource
    }

    enum class DamageSource {
        ENTITY_ATTACK,
        FALL,
        VOID,
        PROJECTILE,
        DROWNING,
        CACTUS,
        LAVA,
        ON_FIRE,
        FIRE,
        ENTITY_EXPLODE,
        MAGIC_EFFECT,
        STARVE,
        API,
        COMMAND,
    }

}