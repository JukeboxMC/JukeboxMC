package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityPufferfish : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(3.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Pufferfish"
    }

    override fun getEntityType(): EntityType {
        return EntityType.PUFFERFISH
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:pufferfish")
    }

    override fun getHeight(): Float {
        return 0.96f
    }

    override fun getWidth(): Float {
        return 0.96f
    }
}