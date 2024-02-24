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
class JukeboxEntityOcelot : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Ocelot"
    }

    override fun getEntityType(): EntityType {
        return EntityType.OCELOT
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:ocelot")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 0.7f else 0.35f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 0.6f else 0.3f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}