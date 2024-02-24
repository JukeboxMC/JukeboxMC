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
class JukeboxEntitySniffer : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(14.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Sniffer"
    }

    override fun getEntityType(): EntityType {
        return EntityType.SNIFFER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:sniffer")
    }

    override fun getHeight(): Float {
        return if(!this.isBaby()) 1.75f else 0.7875f
    }

    override fun getWidth(): Float {
        return if(!this.isBaby()) 1.9f else 0.855f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}