package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.event.entity.ProjectileLaunchEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.projectile.JukeboxEntityEgg
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class ItemEgg(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        val entityEgg = JukeboxEntityEgg()
        entityEgg.setShooter(player)
        entityEgg.setLocation(player.getLocation().add(0f, player.getEyeHeight(), 0f))
        entityEgg.setMotion(clickVector.multiply(1.5f, 1.5f, 1.5f))

        val projectileLaunchEvent = ProjectileLaunchEvent(entityEgg, ProjectileLaunchEvent.Cause.EGG)
        JukeboxServer.getInstance().getPluginManager().callEvent(projectileLaunchEvent)
        if (projectileLaunchEvent.isCancelled()) return false

        if (player.getGameMode() == GameMode.SURVIVAL) {
            player.getInventory().removeItem(Item.Companion.create(ItemType.EGG), 1)
        }
        entityEgg.spawn()
        player.getWorld().playLevelSound(player.getLocation(), SoundEvent.THROW, -1, "minecraft:player")
        return true
    }

}