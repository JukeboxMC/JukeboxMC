package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType
import org.cloudburstmc.protocol.bedrock.packet.EntityEventPacket
import org.jukeboxmc.api.Identifier
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
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin


class ItemCrossbow(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability, Burnable {

    private var canShoot = false

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        if (player.hasAction() && this.getChargedItem() == null) {
            this.setChargedItem(Item.create(ItemType.ARROW).toJukeboxItem())
            player.sendPacket(EntityEventPacket().apply {
                this.runtimeEntityId = player.getEntityId()
                this.data = 0
                this.type = EntityEventType.FINISHED_CHARGING_ITEM
            })
            player.getInventory().sendItemInHand()
            this.canShoot = true
        } else if (this.canShoot && this.getChargedItem() != null) {
            this.shoot(player)
            return false
        }
        return !player.hasAction()
    }

    private fun shoot(player: JukeboxPlayer) {
        if (!player.getInventory().contains(ItemType.ARROW) && player.getGameMode() != GameMode.CREATIVE) return
        player.setAction(false)
        var punchModifier = 0
        val punch = this.getEnchantment(EnchantmentType.PUNCH)
        if (punch != null) {
            punchModifier = punch.getLevel()
        }
        var flameModifier = 0
        val flame = this.getEnchantment(EnchantmentType.FLAME)
        if (flame != null) {
            flameModifier = flame.getLevel()
        }
        val arrow = JukeboxEntityArrow()
        arrow.setShooter(player)
        arrow.setLocation(
            Location(
                player.getWorld(),
                player.getX(),
                player.getY() + player.getEyeHeight(),
                player.getZ(),
                (if (player.getYaw() > 180) 360 else 0) - player.getYaw(),
                -player.getPitch(),
                player.getDimension()
            )
        )
        arrow.setMotion(
            Vector(
                (-sin(player.getYaw() / 180 * Math.PI) * cos(player.getPitch() / 180 * Math.PI)).toFloat(),
                (-sin(player.getPitch() / 180 * Math.PI)).toFloat(),
                (cos(player.getYaw() / 180 * Math.PI) * cos(player.getPitch() / 180 * Math.PI)).toFloat()
            ).multiply(2f, 2f, 2f)
        )
        arrow.setFlameModifier(flameModifier)
        arrow.setPunchModifer(punchModifier)
        arrow.setDamage(7f)
        arrow.setPickupDelay(500, TimeUnit.MILLISECONDS)

        val event = ProjectileLaunchEvent(arrow, ProjectileLaunchEvent.Cause.CROSSBOW)
        JukeboxServer.getInstance().getPluginManager().callEvent(event)
        if (!event.isCancelled()) {
            val enchantmentInfinity = getEnchantment(EnchantmentType.INFINITY)
            if (enchantmentInfinity == null) {
                this.updateDurability(player, 1)
                player.getInventory().setItemInHand(Item.create(ItemType.CROSSBOW).apply {
                    this.setDurability(this@ItemCrossbow.getDurability())
                })
                if (player.getGameMode() == GameMode.SURVIVAL) {
                    player.getInventory().removeItem(Item.create(ItemType.ARROW), 1)
                }
            } else {
                arrow.setInfinityArrow(true)
            }
            arrow.spawn()
            arrow.setOnFire(flameModifier > 0)
            player.playSound(Sound.RANDOM_BOW, 1f, 1f)
        }
    }

    private fun setChargedItem(chargedItem: JukeboxItem?) {
        if (this.getNbt() == null) {
            this.setNbt(NbtMap.EMPTY)
        }
        if (chargedItem != null) {
            if (chargedItem.getNbt() == null) {
                chargedItem.setNbt(NbtMap.EMPTY)
            }
            val chargedItemNbt = chargedItem.toJukeboxItem().toNbt(true)
            this.setNbt(this.getNbt()!!.toBuilder().putCompound("chargedItem", chargedItemNbt).build())
        }
    }

    private fun getChargedItem(): JukeboxItem? {
        if (this.getNbt() != null && this.getNbt()!!.containsKey("chargedItem", NbtType.COMPOUND)) {
            val chargedItem: NbtMap = this.getNbt()!!.getCompound("chargedItem")
            val name = chargedItem.getString("Name", "minecraft:arrow")
            val item = JukeboxItem(ItemRegistry.getItemType(Identifier.fromString(name)), true)
            item.setNbt(chargedItem)
            return item
        }
        return null
    }

    override fun getMaxDurability(): Int {
        return 464
    }

    override fun getBurnTime(): Duration {
        return Duration.ofMillis(200)
    }
}