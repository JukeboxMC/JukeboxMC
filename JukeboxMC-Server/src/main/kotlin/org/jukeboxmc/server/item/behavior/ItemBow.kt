package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.event.entity.ProjectileLaunchEvent
import org.jukeboxmc.api.item.Burnable
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.projectile.JukeboxEntityArrow
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

class ItemBow(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability, Burnable {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        player.setAction(true)
        return super.useInAir(player, clickVector)
    }

    fun shoot(player: JukeboxPlayer) {
        if (player.getActionStart() == -1L) return
        val tick = (JukeboxServer.getInstance().getCurrentTick() - player.getActionStart()) / 20F
        var f = (tick * tick + tick * 2F) / 3F
        if (f > 1F) f = 1F
        val force = f * 3.5f

        player.setAction(false)

        val entityArrow = JukeboxEntityArrow()
        entityArrow.setLocation(Location(
            player.getWorld(),
            player.getX(),
            player.getY() + player.getEyeHeight(),
            player.getZ(),
            (if (player.getYaw() > 180) 360 else 0) - player.getYaw(),
            -player.getPitch(),
            player.getDimension()
        ))
        entityArrow.setMotion(Vector(
            (-sin(player.getYaw() / 180 * Math.PI) * cos(player.getPitch() / 180 * Math.PI)).toFloat(),
            (-sin(player.getPitch() / 180 * Math.PI)).toFloat(),
            (cos(player.getYaw() / 180 * Math.PI) * cos(player.getPitch() / 180 * Math.PI)).toFloat()
        ).multiply(force, force, force))
        entityArrow.setShooter(player)
        entityArrow.setPickupDelay(500, TimeUnit.MILLISECONDS)
        val powerEnchantment = this.getEnchantment(EnchantmentType.POWER)
        if (powerEnchantment != null && powerEnchantment.getLevel() > 0) {
            entityArrow.setDamage(entityArrow.getDamage() + powerEnchantment.getLevel() * 0.5f + 0.5f)
        }
        val punchEnchantment = this.getEnchantment(EnchantmentType.PUNCH)
        if (punchEnchantment != null) {
            entityArrow.setPunchModifer(punchEnchantment.getLevel())
        }
        entityArrow.setCritical(force == 3.5F)

        val projectileLaunchEvent = ProjectileLaunchEvent(entityArrow, ProjectileLaunchEvent.Cause.BOW)
        JukeboxServer.getInstance().getPluginManager().callEvent(projectileLaunchEvent)
        if (projectileLaunchEvent.isCancelled()) return

        val infinityEnchantment = this.hasEnchantment(EnchantmentType.INFINITY) || player.getGameMode() == GameMode.CREATIVE
        entityArrow.setInfinityArrow(infinityEnchantment)
        if (!infinityEnchantment) {
            this.updateDurability(player, 1)
            if (player.getGameMode() == GameMode.SURVIVAL) {
                player.getInventory().removeItem(Item.create(ItemType.ARROW), 1)
            }
        }
        entityArrow.spawn()
        if (this.hasEnchantment(EnchantmentType.FLAME)) {
            entityArrow.setFlameModifier(1)
            entityArrow.setOnFire(true)
        }
        player.playSound(Sound.RANDOM_BOW, 1f, 1f)
    }

    override fun getMaxDurability(): Int {
        return 384
    }

    override fun getBurnTime(): Duration {
        return Duration.ofMillis(200)
    }

}