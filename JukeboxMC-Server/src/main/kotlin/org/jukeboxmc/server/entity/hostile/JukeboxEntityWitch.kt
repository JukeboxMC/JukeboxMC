package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityWitch : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(26.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Witch"
    }

    override fun getEntityType(): EntityType {
        return EntityType.WITCH
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:witch")
    }

    override fun getHeight(): Float {
        return 1.9f
    }

    override fun getWidth(): Float {
        return 0.6f
    }
}