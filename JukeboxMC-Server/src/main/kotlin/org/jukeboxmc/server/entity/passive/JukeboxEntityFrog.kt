package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityFrog : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Frog"
    }

    override fun getEntityType(): EntityType {
        return EntityType.FROG
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:frog")
    }

    override fun getHeight(): Float {
        return 0.5f
    }

    override fun getWidth(): Float {
        return 0.5f
    }
}