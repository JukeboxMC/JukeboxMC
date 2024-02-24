package org.jukeboxmc.server.entity.passive

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Ageable
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.passive.Camel
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityCamel : JukeboxEntityLiving(), Camel, Ageable {

    init {
        this.setMaxHealth(32.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Camel"
    }

    override fun getEntityType(): EntityType {
        return EntityType.CAMEL
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:camel")
    }

    override fun getHeight(): Float {
        return if (!this.isBaby())
            if (!this.isSitting()) 2.375f else 0.945f
        else
            if (!this.isSitting()) 1.1875f else 0.47525f
    }

    override fun getWidth(): Float {
        return if (!this.isBaby()) 1.7f else 0.85f
    }

    override fun isSitting(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.SITTING)
    }

    override fun setSitting(sitting: Boolean) {
        if (this.isSitting() != sitting) {
            this.updateMetadata(this.getMetadata().setFlag(EntityFlag.SITTING, sitting))
        }
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}