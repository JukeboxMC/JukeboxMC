package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

class ItemIronSword(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability {

    override fun addToHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply { this.setCurrentValue(6F) }
    }

    override fun removeFromHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply {
            this.setCurrentValue(this.getMinValue())
        }
    }

    override fun getToolType(): ToolType {
        return ToolType.SWORD
    }

    override fun getTierType(): TierType {
        return TierType.IRON
    }

    override fun getMaxDurability(): Int {
        return 250
    }
}