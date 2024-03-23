package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityBreeze : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Breeze"
    }

    override fun getEntityType(): EntityType {
        return EntityType.BREEZE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:breeze")
    }

    override fun getHeight(): Float {
        return 3.5f
    }

    override fun getWidth(): Float {
        return 3.0f
    }
}