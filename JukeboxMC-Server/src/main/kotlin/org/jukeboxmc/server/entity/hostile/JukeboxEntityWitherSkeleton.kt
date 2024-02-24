package org.jukeboxmc.server.entity.hostile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityWitherSkeleton : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Wither Skeleton"
    }

    override fun getEntityType(): EntityType {
        return EntityType.WITHER_SKELETON
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:wither_skeleton")
    }

    override fun getHeight(): Float {
        return 2.412f
    }

    override fun getWidth(): Float {
        return 0.864f
    }
}