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
class JukeboxEntityCat : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(10.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Cat"
    }

    override fun getEntityType(): EntityType {
        return EntityType.CAT
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:cat")
    }

    override fun getHeight(): Float {
        return if(!this.isBaby()) 0.56f else 0.28f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 0.48f else 0.24f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}