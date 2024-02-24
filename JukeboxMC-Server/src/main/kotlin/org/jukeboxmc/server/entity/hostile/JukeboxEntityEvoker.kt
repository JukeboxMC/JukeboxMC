package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityEvoker : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(24.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Evoker"
    }

    override fun getEntityType(): EntityType {
        return EntityType.EVOKER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:evocation_illager")
    }

    override fun getHeight(): Float {
        return 1.9f
    }

    override fun getWidth(): Float {
        return 0.6f
    }
}