package org.jukeboxmc.server.entity.projectile

import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityLiving
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.projectile.EntityArrow
import org.jukeboxmc.api.event.player.PlayerPickupItemEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.toJukeboxWorld
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt

class JukeboxEntityArrow : JukeboxEntityProjectile(), EntityArrow {

    private var damageValue = 2.0F
    private var pickupDelay: Long = 0
    private var canBePickedUp = false
    private var wasInfinityArrow = false

    private var punchModifier = 0
    private var flameModifier = 0

    override fun tick(currentTick: Long) {
        if (this.isClosed() || this.isDead()) return
        super.tick(currentTick)

        if (this.isOnGround() || this.isCollided() || this.hasCollision()) {
            this.canBePickedUp = true
        }

        if (this.getAge() >= TimeUnit.MINUTES.toMillis(5) / 50) {
            this.remove()
        }
    }

    override fun getHeight(): Float {
        return 0.5f
    }

    override fun getWidth(): Float {
        return 0.5f
    }

    override fun getGravity(): Float {
        return 0.05f
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:arrow")
    }

    override fun getEntityType(): EntityType {
        return EntityType.ARROW
    }

    override fun isCritical(): Boolean {
        return this.getMetadata().getFlag(EntityFlag.CRITICAL)
    }

    override fun setCritical(critical: Boolean) {
        this.updateMetadata(this.getMetadata().setFlag(EntityFlag.CRITICAL, critical))
    }

    override fun isInfinityArrow(): Boolean {
        return this.wasInfinityArrow
    }

    override fun setInfinityArrow(infinityArrow: Boolean) {
        this.wasInfinityArrow = infinityArrow
    }

    override fun getPickupDelay(): Long {
        return this.pickupDelay
    }

    override fun setPickupDelay(duration: Long, timeUnit: TimeUnit) {
        this.pickupDelay = JukeboxServer.getInstance().getCurrentTick() + timeUnit.toMillis(duration) / 50
    }

    override fun getPunchModifier(): Int {
        return this.punchModifier
    }

    override fun setPunchModifer(punchModifier: Int) {
        this.punchModifier = punchModifier
    }

    override fun getFlameModifier(): Int {
        return this.flameModifier
    }

    override fun setFlameModifier(flameModifier: Int) {
        this.flameModifier = flameModifier
    }

    override fun onCollideWithPlayer(player: Player) {
        if (JukeboxServer.getInstance().getCurrentTick() > this.pickupDelay && this.canBePickedUp && !this.isClosed() && !player.isDead()) {
            val arrow = Item.create(ItemType.ARROW)
            if (!player.getInventory().canAddItem(arrow)) return
            val pickupItemEvent = PlayerPickupItemEvent(player, arrow)
            JukeboxServer.getInstance().getPluginManager().callEvent(pickupItemEvent)
            if (pickupItemEvent.isCancelled()) return
            this.remove()
            player.getWorld().toJukeboxWorld().sendLevelEvent(player.getLocation(), LevelEvent.SOUND_INFINITY_ARROW_PICKUP)
            if (!this.wasInfinityArrow || player.getGameMode() == GameMode.CREATIVE) {
                player.getInventory().addItem(arrow)
            }
        } else if (this.getHitEntity() != null) {
            this.remove()
        }
    }

    override fun getDamage(): Float {
        return this.damageValue
    }

    fun setDamage(damage: Float) {
        this.damageValue = damage
    }

    override fun applyCustomDamageEffects(hitEntity: Entity?) {
        if (hitEntity == null) return
        if (this.flameModifier > 0 && hitEntity is EntityLiving) {
            hitEntity.setOnFire(5, TimeUnit.SECONDS)
        }
    }

    override fun applyCustomKnockback(hitEntity: Entity?) {
        if (hitEntity == null) return
        if (this.punchModifier > 0) {
            val velocity = this.getVelocity()
            val sqrtMotion = sqrt((velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ()))
            if (sqrtMotion > 0.0f) {
                hitEntity.setVelocity(hitEntity.getVelocity().add(
                    velocity.getX() * punchModifier * 0.6f / sqrtMotion,
                    0.1f,
                    velocity.getZ() * punchModifier * 0.6f / sqrtMotion))
            }
        }
    }
}