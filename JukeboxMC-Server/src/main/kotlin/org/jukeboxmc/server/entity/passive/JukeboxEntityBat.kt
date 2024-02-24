package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityBat : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(6.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Bat"
    }

    override fun getEntityType(): EntityType {
        return EntityType.BAT
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:bat")
    }

    override fun getHeight(): Float {
        return 0.9f
    }

    override fun getWidth(): Float {
        return 0.5f
    }
}