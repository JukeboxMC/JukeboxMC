package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.event.entity.ProjectileLaunchEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.projectile.JukeboxEntitySnowball
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class ItemSnowball(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        val entitySnowball = JukeboxEntitySnowball()
        entitySnowball.setShooter(player)
        entitySnowball.setLocation(player.getLocation().add(0f, player.getEyeHeight(), 0f))
        entitySnowball.setMotion(clickVector.multiply(1.5f, 1.5f, 1.5f))

        val projectileLaunchEvent = ProjectileLaunchEvent(entitySnowball, ProjectileLaunchEvent.Cause.EGG)
        JukeboxServer.getInstance().getPluginManager().callEvent(projectileLaunchEvent)
        if (projectileLaunchEvent.isCancelled()) return false

        if (player.getGameMode() == GameMode.SURVIVAL) {
            player.getInventory().removeItem(Item.Companion.create(ItemType.EGG), 1)
        }
        entitySnowball.spawn()
        player.getWorld().playLevelSound(player.getLocation(), SoundEvent.THROW, -1, "minecraft:player")
        return true
    }

}