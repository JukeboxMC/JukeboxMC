package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityAllay : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Allay"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ALLAY
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:allay")
    }

    override fun getHeight(): Float {
        return 0.6f
    }

    override fun getWidth(): Float {
        return 0.6f
    }
}