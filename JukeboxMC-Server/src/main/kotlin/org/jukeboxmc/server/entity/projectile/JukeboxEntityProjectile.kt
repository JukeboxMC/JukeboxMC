package org.jukeboxmc.server.entity.projectile

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityLiving
import org.jukeboxmc.api.entity.projectile.EntityProjectile
import org.jukeboxmc.api.event.entity.EntityDamageByEntityEvent
import org.jukeboxmc.api.event.entity.EntityDamageEvent
import org.jukeboxmc.api.event.entity.ProjectileHitEntityEvent
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.JukeboxEntity
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.Utils
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

open class JukeboxEntityProjectile : JukeboxEntity(), EntityProjectile {

    private var shooter: Entity? = null
    private var hitEntity: Entity? = null
    private var hadCollision = false

    override fun tick(currentTick: Long) {
        if (this.isClosed() || this.isDead()) return
        super.tick(currentTick)
        var location = this.getLocation()
        if (!this.isDead()) {
            if (this.hitEntity != null) {
                location = this.hitEntity!!.getLocation().add(0f, this.hitEntity!!.getEyeHeight() + getHeight(), 0f)
                this.setLocation(location)
            } else {
                val velocity = this.getVelocity()
                if (!this.isCollided()) {
                    velocity.setY(velocity.getY() - this.getGravity())
                }
                val moveVector = Vector(
                    location.getX() + velocity.getX(),
                    location.getY() + velocity.getY(),
                    location.getZ() + velocity.getZ()
                )
                val nearbyEntities: Collection<Entity> = location.getWorld().getNearbyEntities(
                    this.getBoundingBox().addCoordinates(
                        velocity.getX(), velocity.getY(), velocity.getZ()
                    ).expand(1F, 1F, 1F), location.getDimension(), this
                )
                var nearDistance = Int.MAX_VALUE.toFloat()
                var hitEntity: Entity? = null
                for (entity in nearbyEntities) {
                    if (entity === this.shooter && this.getAge() < 20) {
                        continue
                    }
                    val axisAlignedBB = entity.getBoundingBox().grow(0.3f, 0.3f, 0.3f)
                    val onLineVector: Vector = axisAlignedBB.calculateIntercept(location, moveVector) ?: continue
                    val distance: Float = location.distanceSquared(onLineVector)
                    if (distance < nearDistance) {
                        nearDistance = distance
                        hitEntity = entity
                    }
                }
                if (hitEntity != null) {
                    val projectileHitEntityEvent = ProjectileHitEntityEvent(hitEntity, this)
                    JukeboxServer.getInstance().getPluginManager().callEvent(projectileHitEntityEvent)
                    if (projectileHitEntityEvent.isCancelled()) {
                        return
                    }
                    var damage: Float = this.getDamage()
                    if (this is JukeboxEntityArrow) {
                        val f2 = sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY() + velocity.getZ() * velocity.getZ())
                        damage = Utils.ceilOrRound(f2 * damage).toFloat()

                        if (this.isCritical()) {
                            damage += kotlin.random.Random.nextInt((damage / 2 + 2).toInt())
                        }
                    }
                    val event = EntityDamageByEntityEvent(hitEntity, this.shooter, damage, EntityDamageEvent.DamageSource.PROJECTILE)
                    if (hitEntity.damage(event)) {
                        this.applyCustomKnockback(hitEntity)
                        this.applyCustomDamageEffects(hitEntity)
                        if (this is JukeboxEntityArrow) {
                            this.shooter?.let {
                                if (it is JukeboxPlayer) {
                                    it.playSound(Sound.RANDOM_BOWHIT, 1f, 1f)
                                }
                            }
                        }
                        this.updateMetadata(this.getMetadata().setLong(EntityDataTypes.TARGET_EID, hitEntity.getEntityId()))
                    }
                    this.onCollidedWithEntity(hitEntity)
                    this.hitEntity = hitEntity
                    this.updateMovement()
                    this.onCollidedEffect()
                }

                this.move(velocity)

                if (this.isCollided() && !this.hadCollision) {
                    this.hadCollision = true
                    velocity.setX(0F)
                    velocity.setY(0F)
                    velocity.setZ(0F)
                    this.setLocation(location)
                    this.setMotion(velocity)
                    this.updateMovement()
                    this.onCollidedEffect()
                    return
                } else if (!this.isCollided() && this.hadCollision) {
                    this.hadCollision = false
                }
                if (!this.hadCollision || abs(velocity.getX()) > 0.00001 || abs(velocity.getY()) > 0.00001 || abs(
                        velocity.getZ()
                    ) > 0.00001
                ) {
                    val f = sqrt((velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ()).toDouble())
                    location.setYaw(
                        (atan2(
                            velocity.getX().toDouble(),
                            velocity.getZ().toDouble()
                        ) * 180 / Math.PI).toFloat()
                    )
                    location.setPitch((atan2(velocity.getY().toDouble(), f) * 180 / Math.PI).toFloat())
                }
                this.setLocation(location)
                this.setMotion(velocity)
                this.updateMovement()
            }
        }
    }

    override fun getShooter(): Entity? {
        return this.shooter
    }

    override fun setShooter(entity: Entity?) {
        this.shooter = entity
        entity?.let { this.getMetadata().setLong(EntityDataTypes.OWNER_EID, it.getEntityId()) }
    }

    override fun canCollideWith(entity: Entity): Boolean {
        return entity is EntityLiving && !this.isOnGround()
    }

    open fun getDamage(): Float {
        return 0f
    }

    open fun applyCustomDamageEffects(hitEntity: Entity?) {}

    open fun applyCustomKnockback(hitEntity: Entity?) {}

    open fun onCollidedWithEntity(entity: Entity?) {}

    open fun onCollidedEffect() {}

    fun getHitEntity(): Entity? {
        return this.hitEntity
    }

    fun hadCollision(): Boolean {
        return this.hadCollision
    }
}