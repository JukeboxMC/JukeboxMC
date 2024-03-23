package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.event.entity.ProjectileLaunchEvent
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.projectile.JukeboxEntityFishingHook
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.cos
import kotlin.math.sin

class ItemFishingRod(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        if (player.getEntityFishingHook() == null) {
            val force = 1.1f
            val entityFishingHook = JukeboxEntityFishingHook()
            entityFishingHook.setLocation(player.getLocation().add(0.5f, player.getEyeHeight(), 0.5f))
            entityFishingHook.setMotion(Vector(
                (-sin(Math.toRadians(player.getYaw().toDouble())) * cos(Math.toRadians(player.getPitch().toDouble())) * force * force).toFloat(),
                    (-sin(Math.toRadians(player.getPitch().toDouble())) * force * force + 0.4f).toFloat(),
                    (cos(Math.toRadians(player.getYaw().toDouble())) * cos(Math.toRadians(player.getPitch().toDouble())) * force * force).toFloat()
                ))
            entityFishingHook.setShooter(player)

            val projectileLaunchEvent = ProjectileLaunchEvent(entityFishingHook, ProjectileLaunchEvent.Cause.FISHING_ROD)
            JukeboxServer.getInstance().getPluginManager().callEvent(projectileLaunchEvent)
            if (projectileLaunchEvent.isCancelled()) return false
            entityFishingHook.spawn()
            this.updateDurability(player, 1)
            player.setEntityFishingHook(entityFishingHook)
            player.getWorld().playLevelSound(player.getLocation(), SoundEvent.THROW, -1, "minecraft:player")
            return true
        } else {
            player.getEntityFishingHook()?.remove()
            player.setEntityFishingHook(null)
        }
        return true
    }

    override fun getMaxDurability(): Int {
        return 384
    }

}