package org.jukeboxmc.server.entity.neutral

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityIronGolem : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(100.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Iron Golem"
    }

    override fun getEntityType(): EntityType {
        return EntityType.IRON_GOLEM
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:iron_golem")
    }

    override fun getHeight(): Float {
        return 2.9f
    }

    override fun getWidth(): Float {
        return 1.4f
    }
}