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
class JukeboxEntityBee : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Bee"
    }

    override fun getEntityType(): EntityType {
        return EntityType.BEE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:bee")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 0.5f else 0.25f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 0.55f else 0.275f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}