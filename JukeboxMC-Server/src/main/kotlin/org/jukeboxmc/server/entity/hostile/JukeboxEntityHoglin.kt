package org.jukeboxmc.server.entity.hostile

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Ageable
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityHoglin : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(40.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Hoglin"
    }

    override fun getEntityType(): EntityType {
        return EntityType.HOGLIN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:hoglin")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 1.4f else 0.7f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 1.3965f else 0.6982f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}