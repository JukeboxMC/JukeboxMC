package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityRavager : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(100.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Ravager"
    }

    override fun getEntityType(): EntityType {
        return EntityType.RAVAGER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:ravager")
    }

    override fun getHeight(): Float {
        return 2.2f
    }

    override fun getWidth(): Float {
        return 1.95f
    }
}