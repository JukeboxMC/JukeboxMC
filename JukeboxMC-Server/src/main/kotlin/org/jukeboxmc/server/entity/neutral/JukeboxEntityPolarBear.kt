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
class JukeboxEntityPolarBear : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Polar Bear"
    }

    override fun getEntityType(): EntityType {
        return EntityType.POLAR_BEAR
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:polar_bear")
    }

    override fun getHeight(): Float {
        return if(!this.isBaby()) 1.4f else 0.7f
    }

    override fun getWidth(): Float {
        return if(!this.isBaby()) 1.4f else 0.7f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}