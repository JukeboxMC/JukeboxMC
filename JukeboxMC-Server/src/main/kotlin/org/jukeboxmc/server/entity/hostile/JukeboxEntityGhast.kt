package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityGhast : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Ghast"
    }

    override fun getEntityType(): EntityType {
        return EntityType.GHAST
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:ghast")
    }

    override fun getHeight(): Float {
        return 4.0f
    }

    override fun getWidth(): Float {
        return 4.0f
    }
}