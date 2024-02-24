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
class JukeboxEntityElderGuardian : JukeboxEntityLiving(), Ageable {

    init {
        this.setMaxHealth(80.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Elder Guardian"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ELDER_GUARDIAN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:elder_guardian")
    }

    override fun getHeight(): Float {
        return 1.9975f
    }

    override fun getWidth(): Float {
        return 1.9975f
    }

    override fun isBaby(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.BABY)
    }

    override fun setBaby(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.BABY, value)
        this.updateMetadata()
    }
}