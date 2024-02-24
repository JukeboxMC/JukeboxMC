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
class JukeboxEntityTurtle : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(30.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Turtle"
    }

    override fun getEntityType(): EntityType {
        return EntityType.TURTLE
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:turtle")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby()) 0.4f else 0.032f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 1.2f else 0.096f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}