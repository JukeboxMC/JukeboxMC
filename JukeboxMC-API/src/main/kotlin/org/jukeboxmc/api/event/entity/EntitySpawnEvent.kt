package org.jukeboxmc.api.event.entity

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.Cancellable

class EntitySpawnEvent(
    entity: Entity
) : EntityEvent(entity), Cancellable