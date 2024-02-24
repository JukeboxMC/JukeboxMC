package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityShulker : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Shulker"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SHULKER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:shulker")
    }

    override fun getHeight(): Float {
        return 1.0f
    }

    override fun getWidth(): Float {
        return 1.0f
    }
}