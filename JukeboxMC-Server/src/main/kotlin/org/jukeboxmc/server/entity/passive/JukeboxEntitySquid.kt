package org.jukeboxmc.server.entity.passive

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Ageable
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntitySquid : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Squid"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SQUID
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:squid")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 0.95f else 0.475f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 0.95f else 0.475f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}