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
class JukeboxEntityEnderDragon : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(200.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Ender Dragon"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ENDER_DRAGON
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:ender_dragon")
    }

    override fun getHeight(): Float {
        return 8.0f
    }

    override fun getWidth(): Float {
        return 16.0f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}