package org.jukeboxmc.server.entity.neutral

import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Angryable
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.JukeboxEntityLiving

/**
 * @author Kaooot
 * @version 1.0
 */
class JukeboxEntityEnderman : JukeboxEntityLiving(), Angryable {

    init {
        this.setMaxHealth(40.0f)
        this.setHealth(this.getMaxHealth())
    }

    override fun getName(): String {
        return "Enderman"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ENDERMAN
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:enderman")
    }

    override fun getHeight(): Float {
        return if (!this.isAngry()) 2.9f else 3.25f
    }

    override fun getWidth(): Float {
        return 0.6f
    }

    override fun isAngry(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.ANGRY)
    }

    override fun setAngry(value: Boolean) {
        this.getMetadata().setFlag(EntityFlag.ANGRY, value)
        this.updateMetadata()
    }
}