package org.jukeboxmc.server.entity.neutral

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntitySpider : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(16.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Spider"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SPIDER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:spider")
    }

    override fun getHeight(): Float {
        return 0.9f
    }

    override fun getWidth(): Float {
        return 1.4f
    }
}