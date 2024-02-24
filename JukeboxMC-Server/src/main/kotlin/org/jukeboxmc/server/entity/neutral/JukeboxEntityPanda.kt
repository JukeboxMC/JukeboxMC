package org.jukeboxmc.server.entity.neutral

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Ageable
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityPanda : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Panda"
    }

    override fun getEntityType(): EntityType {
        return EntityType.PANDA
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:panda")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 1.25f else 0.625f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 1.3f else 0.65f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}