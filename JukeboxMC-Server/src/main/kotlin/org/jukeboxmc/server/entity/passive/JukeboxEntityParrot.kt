package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityParrot : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(6.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Parrot"
    }

    override fun getEntityType(): EntityType {
        return EntityType.PARROT
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:parrot")
    }

    override fun getHeight(): Float {
        return 1.0f
    }

    override fun getWidth(): Float {
        return 0.5f
    }
}