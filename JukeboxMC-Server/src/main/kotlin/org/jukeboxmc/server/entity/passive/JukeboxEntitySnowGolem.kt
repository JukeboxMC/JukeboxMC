package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntitySnowGolem : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(4.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Snow Golem"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SNOW_GOLEM
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:snow_golem")
    }

    override fun getHeight(): Float {
        return 1.8f
    }

    override fun getWidth(): Float {
        return 0.4f
    }
}