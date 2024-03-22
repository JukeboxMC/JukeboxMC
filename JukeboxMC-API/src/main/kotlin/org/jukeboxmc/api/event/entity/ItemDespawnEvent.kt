package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.item.EntityItem
import org.jukeboxmc.api.event.Cancellable

class ItemDespawnEvent(
    private val entity: EntityItem,
) : EntityEvent(entity), Cancellable {

    override fun getEntity(): EntityItem {
        return this.entity
    }

}