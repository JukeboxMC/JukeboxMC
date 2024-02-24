package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityTropicalFish : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(3.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Tropical Fish"
    }

    override fun getEntityType(): EntityType {
        return EntityType.TROPICAL_FISH
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:tropicalfish")
    }

    override fun getHeight(): Float {
        return 0.52f
    }

    override fun getWidth(): Float {
        return 0.52f
    }
}