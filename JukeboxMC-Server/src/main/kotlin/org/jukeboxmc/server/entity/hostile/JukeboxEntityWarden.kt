package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityWarden : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(500.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Warden"
    }

    override fun getEntityType(): EntityType {
        return EntityType.WARDEN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:warden")
    }

    override fun getHeight(): Float {
        return 2.9f
    }

    override fun getWidth(): Float {
        return 0.9f
    }
}