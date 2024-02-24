package org.jukeboxmc.api.entity.passive

import org.jukeboxmc.api.entity.Ageable
import org.jukeboxmc.api.entity.EntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
interface Camel : EntityLiving, Ageable {

    fun isSitting() : Boolean

    fun setSitting(sitting: Boolean)
}