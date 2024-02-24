package org.jukeboxmc.server.entity.passive

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityAxolotl : JukeboxEntityLiving() {

    init {
        this.setMaxHealth(14.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Axolotl"
    }

    override fun getEntityType(): EntityType {
        return EntityType.AXOLOTL
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:axolotl")
    }

    override fun getHeight(): Float {
        return 0.42f
    }

    override fun getWidth(): Float {
        return 0.75f
    }
}