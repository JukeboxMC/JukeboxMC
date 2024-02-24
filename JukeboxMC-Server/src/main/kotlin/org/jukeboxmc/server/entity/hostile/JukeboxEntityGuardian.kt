package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityGuardian : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Guardian"
    }

    override fun getEntityType(): EntityType {
        return EntityType.GUARDIAN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:guardian")
    }

    override fun getHeight(): Float {
        return 0.85f
    }

    override fun getWidth(): Float {
        return 0.85f
    }
}