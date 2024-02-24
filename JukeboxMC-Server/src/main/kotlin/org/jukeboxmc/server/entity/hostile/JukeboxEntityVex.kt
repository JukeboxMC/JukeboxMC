package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityVex : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(14.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Vex"
    }

    override fun getEntityType(): EntityType {
        return EntityType.VEX
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:vex")
    }

    override fun getHeight(): Float {
        return 0.8f
    }

    override fun getWidth(): Float {
        return 0.4f
    }
}