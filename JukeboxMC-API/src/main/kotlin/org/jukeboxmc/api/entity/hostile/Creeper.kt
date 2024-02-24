package org.jukeboxmc.api.entity.hostile

import org.jukeboxmc.api.entity.EntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
interface Creeper : EntityLiving {

    fun isCharged(): Boolean

    fun setCharged(charged: Boolean)
}