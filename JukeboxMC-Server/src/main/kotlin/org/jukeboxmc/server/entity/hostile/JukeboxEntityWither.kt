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
class JukeboxEntityWither : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(300.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Wither"
    }

    override fun getEntityType(): EntityType {
        return EntityType.WITHER
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:wither")
    }

    override fun getHeight(): Float {
        return 3.0f
    }

    override fun getWidth(): Float {
        return 1.0f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}