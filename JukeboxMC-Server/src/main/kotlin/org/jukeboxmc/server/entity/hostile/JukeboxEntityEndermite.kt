package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityEndermite : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(8.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Endermite"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ENDERMITE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:endermite")
    }

    override fun getHeight(): Float {
        return 0.3f
    }

    override fun getWidth(): Float {
        return 0.4f
    }
}