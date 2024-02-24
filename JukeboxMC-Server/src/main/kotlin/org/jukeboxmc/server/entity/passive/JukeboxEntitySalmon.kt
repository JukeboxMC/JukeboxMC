package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntitySalmon : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(3.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Salmon"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SALMON
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:salmon")
    }

    override fun getHeight(): Float {
        return 0.75f
    }

    override fun getWidth(): Float {
        return 0.75f
    }
}