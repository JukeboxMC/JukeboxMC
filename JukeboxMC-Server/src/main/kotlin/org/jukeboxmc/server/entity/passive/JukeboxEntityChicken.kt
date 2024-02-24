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
class JukeboxEntityChicken : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(4.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Chicken"
    }

    override fun getEntityType(): EntityType {
        return EntityType.CHICKEN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:chicken")
    }

    override fun getHeight(): Float {
        return if(!this.isBaby()) 0.8f else 0.4f
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