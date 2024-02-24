package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityBlaze : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Blaze"
    }

    override fun getEntityType(): EntityType {
        return EntityType.BLAZE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:blaze")
    }

    override fun getHeight(): Float {
        return 1.8f
    }

    override fun getWidth(): Float {
        return 0.5f
    }
}