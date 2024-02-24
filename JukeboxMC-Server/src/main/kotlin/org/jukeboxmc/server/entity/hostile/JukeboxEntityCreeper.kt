package org.jukeboxmc.server.entity.hostile

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.hostile.Creeper
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityCreeper : JukeboxEntityLiving(), Creeper {

    init {
        this.setMaxHealth(20.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Creeper"
    }

    override fun getEntityType(): EntityType {
        return EntityType.CREEPER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:creeper")
    }

    override fun getHeight(): Float {
        return 1.8f
    }

    override fun getWidth(): Float {
        return 0.6f
    }

    override fun isCharged(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.CHARGED)
    }

    override fun setCharged(charged: Boolean) {
        if (this.isCharged() != charged) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.CHARGED, charged))
        }
    }
}