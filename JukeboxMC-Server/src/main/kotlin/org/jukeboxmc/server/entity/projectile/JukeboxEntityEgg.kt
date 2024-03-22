package org.jukeboxmc.server.entity.projectile

import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.world.Particle
import org.jukeboxmc.server.extensions.toJukeboxWorld
import java.util.concurrent.TimeUnit

class JukeboxEntityEgg : JukeboxEntityProjectile() {

    override fun tick(currentTick: Long) {
        if (this.isClosed() || this.isDead()) return
        super.tick(currentTick)

        if ((this.getAge() >= TimeUnit.MINUTES.toMillis(5) / 50) || this.isCollided()) {
            this.remove()
        }
    }

    override fun getName(): String {
        return "Egg"
    }

    override fun getWidth(): Float {
        return 0.25f
    }

    override fun getHeight(): Float {
        return 0.25f
    }

    override fun getGravity(): Float {
        return 0.03f
    }

    override fun getEntityType(): EntityType {
        return EntityType.EGG
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:egg")
    }

    override fun onCollidedEffect() {
        val item = Item.create(ItemType.EGG, 1, 14)
        for (i in 0..6) {
            this.getWorld().toJukeboxWorld().sendLevelEvent(this.getLocation().add(0f, 0.5f, 0f), LevelEvent.PARTICLE_GENERIC_SPAWN,
                item.getNetworkId() shl 16 or item.getMeta()
            )
        }
    }

}