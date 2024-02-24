package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityTadpole : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(6.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Tadpole"
    }

    override fun getEntityType(): EntityType {
        return EntityType.TADPOLE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:tadpole")
    }

    override fun getHeight(): Float {
        return 0.6f
    }

    override fun getWidth(): Float {
        return 0.8f
    }
}