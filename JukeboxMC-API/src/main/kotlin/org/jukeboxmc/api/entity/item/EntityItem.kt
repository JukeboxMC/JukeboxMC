package org.jukeboxmc.api.entity.item

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.item.Item
import java.util.concurrent.TimeUnit

interface EntityItem : Entity {

    fun getItem(): Item

    fun setItem(item: Item)

    fun getPickupDelay(): Long

    fun setPickupDelay(pickupDelay: Long, timeUnit: TimeUnit)

    fun getThrower(): EntityHuman?

    fun setThrower(thrower: EntityHuman?)

}