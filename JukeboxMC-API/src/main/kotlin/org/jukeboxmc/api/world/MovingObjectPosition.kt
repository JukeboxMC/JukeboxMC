package org.jukeboxmc.api.world

import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.math.Vector


class MovingObjectPosition private constructor(
    private val blockPosition: Vector? = null,
    var type: EnumMovingObjectType,
    var direction: BlockFace? = null,
    var position: Vector,
    var entity: Entity? = null
) {
    constructor(position: Vector, blockFace: BlockFace?, blockPosition: Vector) : this(blockPosition, EnumMovingObjectType.BLOCK, blockFace, position)

    constructor(position: Vector, blockFace: BlockFace?) : this(null, EnumMovingObjectType.BLOCK, blockFace, position, null)

    constructor(entity: Entity) : this(entity, Vector(entity.getX(), entity.getY(), entity.getZ()))

    constructor(type: EnumMovingObjectType, position: Vector, blockFace: BlockFace?, blockPosition: Vector) : this(blockPosition, type, blockFace, Vector(position.getX(), position.getY(), position.getZ()), null)

    constructor(entity: Entity, position: Vector) : this(null, EnumMovingObjectType.ENTITY, null, position, entity)


    override fun toString(): String {
        return "HitResult{type=$type, blockpos=$blockPosition, f=$direction, pos=$position, entity=$entity}"
    }

    enum class EnumMovingObjectType {
        MISS, BLOCK, ENTITY
    }
}
