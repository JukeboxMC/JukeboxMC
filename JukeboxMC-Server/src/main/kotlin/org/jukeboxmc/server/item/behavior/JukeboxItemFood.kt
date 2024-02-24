package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.event.player.PlayerConsumeItemEvent
import org.jukeboxmc.api.item.Food
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.inventory.ContainerInventory
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

abstract class JukeboxItemFood(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Food {

    override fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        if (player.isHungry() || player.getGameMode() == GameMode.CREATIVE) {
            return true
        }
        player.setHunger(player.getHunger())
        player.getInventory().sendContents(player)
        return false
    }

    override fun onUse(player: JukeboxPlayer): Boolean {
        if (player.isHungry()) {
            val playerConsumeItemEvent = PlayerConsumeItemEvent(player, this)
            JukeboxServer.getInstance().getPluginManager().callEvent(playerConsumeItemEvent)
            if (playerConsumeItemEvent.isCancelled()) {
                (player.getInventory() as ContainerInventory).sendContents(player)
                return false
            }
            player.addHunger(getHunger())
            val saturation = (player.getSaturation() + this.getSaturation()).coerceAtMost(player.getHunger().toFloat())
            player.setSaturation(saturation)
            this.setAmount(this.getAmount() - 1)
            player.getInventory().setItemInHand(this)
            return true
        }
        return false
    }

}