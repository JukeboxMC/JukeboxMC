package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.*
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import java.time.Duration

class ItemWoodenAxe(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability, Burnable {

    override fun addToHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply { this.setCurrentValue(3F) }
    }

    override fun removeFromHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply {
            this.setCurrentValue(this.getMinValue())
        }
    }

    override fun getBurnTime(): Duration {
        return Duration.ofMillis(200)
    }

    override fun getToolType(): ToolType {
        return ToolType.AXE
    }

    override fun getTierType(): TierType {
        return TierType.WOODEN
    }

    override fun getMaxDurability(): Int {
        return 59
    }
}