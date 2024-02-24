package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityMagmaCube : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(1.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Magma Cube"
    }

    override fun getEntityType(): EntityType {
        return EntityType.MAGMA_CUBE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:magma_cube")
    }

    override fun getHeight(): Float {
        return 0.52f
    }

    override fun getWidth(): Float {
        return 0.52f
    }
}