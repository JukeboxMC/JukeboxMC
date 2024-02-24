package org.jukeboxmc.server.entity.neutral

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityPiglin : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(16.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Piglin"
    }

    override fun getEntityType(): EntityType {
        return EntityType.PIGLIN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:piglin")
    }

    override fun getHeight(): Float {
        return 1.9f
    }

    override fun getWidth(): Float {
        return 0.6f
    }
}