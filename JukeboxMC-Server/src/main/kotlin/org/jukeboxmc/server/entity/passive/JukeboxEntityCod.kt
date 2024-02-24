package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityCod : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(3.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Cod"
    }

    override fun getEntityType(): EntityType {
        return EntityType.COD
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:cod")
    }

    override fun getHeight(): Float {
        return 0.3f
    }

    override fun getWidth(): Float {
        return 0.5f
    }
}