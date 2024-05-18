package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityArmadillo : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(6.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Armadillo"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ARMADILLO
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:armadillo")
    }

    override fun getHeight(): Float {
        return 2.5f
    }

    override fun getWidth(): Float {
        return 3.0f
    }
}