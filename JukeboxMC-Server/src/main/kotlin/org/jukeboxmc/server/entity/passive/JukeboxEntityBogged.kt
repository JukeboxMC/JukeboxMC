package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityBogged : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Bogged"
    }

    override fun getEntityType(): EntityType {
        return EntityType.BOGGED
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:bogged")
    }

    override fun getHeight(): Float {
        return 2.5f
    }

    override fun getWidth(): Float {
        return 1.5f
    }
}