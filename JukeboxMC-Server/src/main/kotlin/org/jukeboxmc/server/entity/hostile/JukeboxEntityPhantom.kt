package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityPhantom : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Phantom"
    }

    override fun getEntityType(): EntityType {
        return EntityType.PHANTOM
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:phantom")
    }

    override fun getHeight(): Float {
        return 0.5f
    }

    override fun getWidth(): Float {
        return 0.9f
    }
}