package org.jukeboxmc.server.entity.neutral

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityCaveSpider : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(12.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Cave Spider"
    }

    override fun getEntityType(): EntityType {
        return EntityType.CAVE_SPIDER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:cave_spider")
    }

    override fun getHeight(): Float {
        return 0.5f
    }

    override fun getWidth(): Float {
        return 0.7f
    }
}