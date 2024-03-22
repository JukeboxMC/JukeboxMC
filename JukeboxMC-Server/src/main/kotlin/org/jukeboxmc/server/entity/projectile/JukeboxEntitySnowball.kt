package org.jukeboxmc.server.entity.projectile

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.world.Particle
import java.util.concurrent.TimeUnit

class JukeboxEntitySnowball : JukeboxEntityProjectile() {

    override fun tick(currentTick: Long) {
        if (this.isClosed() || this.isDead()) return
        super.tick(currentTick)

        if ((this.getAge() >= TimeUnit.MINUTES.toMillis(5) / 50) || this.isCollided()) {
            this.remove()
        }
    }

    override fun getName(): String {
        return "Snowball"
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
        return EntityType.SNOWBALL
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:snowball")
    }

    override fun onCollidedEffect() {
        for (i in 0..5) {
            this.getWorld().spawnParticle(Particle.PARTICLE_GENERIC_SPAWN, this.getLocation().add(0f, 0.5f, 0f), 15)
        }
    }

    override fun onCollidedWithEntity(entity: Entity?) {
        this.remove()
    }

}