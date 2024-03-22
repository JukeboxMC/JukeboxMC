package org.jukeboxmc.api.entity.projectile

import java.util.concurrent.TimeUnit

interface EntityArrow : EntityProjectile {

    fun isCritical(): Boolean

    fun setCritical(critical: Boolean)

    fun isInfinityArrow(): Boolean

    fun setInfinityArrow(infinityArrow: Boolean)

    fun getPickupDelay(): Long

    fun setPickupDelay(duration: Long, timeUnit: TimeUnit)

    fun getPunchModifier(): Int

    fun setPunchModifer(punchModifier: Int)

    fun getFlameModifier(): Int

    fun setFlameModifier(flameModifier: Int)

}