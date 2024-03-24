package org.jukeboxmc.api.entity

import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.event.entity.EntityDamageEvent
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.chunk.Chunk
import java.util.concurrent.TimeUnit

/**
 * @author Kaooot
 * @version 1.0
 */
interface Entity {

    companion object {
        fun create(entityType: EntityType): Entity? {
            return JukeboxMC.getServer().createEntity(entityType)
        }

        fun <T> create(entityType: EntityType): T? {
            return JukeboxMC.getServer().createEntity(entityType)
        }
    }

    /**
     * Returns the exact display name of the entity
     *
     * @return the name of the entity
     */
    fun getName(): String

    fun getEntityId(): Long

    fun getIdentifier(): Identifier

    fun getEntityType(): EntityType

    fun getBoundingBox(): AxisAlignedBB

    fun getVelocity(): Vector

    fun setVelocity(velocity: Vector)

    fun setMotion(motion: Vector)

    fun getLocation(): Location

    fun setLocation(location: Location)

    fun getWorld(): World

    fun getX(): Float

    fun setX(x: Float)

    fun getY(): Float

    fun setY(y: Float)

    fun getZ(): Float

    fun setZ(z: Float)

    fun getBlockX(): Int

    fun getBlockY(): Int

    fun getBlockZ(): Int

    fun getYaw(): Float

    fun setYaw(yaw: Float)

    fun getPitch(): Float

    fun setPitch(pitch: Float)

    fun getChunkX(): Int

    fun getChunkZ(): Int

    fun getChunk(): Chunk?

    fun getLoadedChunk(): Chunk?

    fun getDimension(): Dimension

    fun getEyeHeight(): Float

    fun getWidth(): Float

    fun getHeight(): Float

    fun isClosed(): Boolean

    fun isOnGround(): Boolean

    fun isDead(): Boolean

    fun getAge(): Long

    fun setAge(age: Long)

    fun getDirection(): Direction

    fun getMaxAirSupply(): Short

    fun setMaxAirSupply(value: Short)

    fun getScale(): Float

    fun setScale(value: Float)

    fun hasCollision(): Boolean

    fun setCollision(value: Boolean)

    fun hasGravity(): Boolean

    fun setGravity(value: Boolean)

    fun getNameTag(): String

    fun setNameTag(value: String)

    fun isNameTagVisible(): Boolean

    fun setNameTagVisible(value: Boolean)

    fun isNameTagAlwaysVisible(): Boolean

    fun setNameTagAlwaysVisible(value: Boolean)

    fun canClimb(): Boolean

    fun setCanClimb(value: Boolean)

    fun isInvisible(): Boolean

    fun setInvisible(value: Boolean)

    fun getFireTicks(): Long

    fun isOnFire(): Boolean

    fun setOnFire(value: Boolean)

    fun setOnFire(value: Long, timeUnit: TimeUnit)

    fun hasNoAI(): Boolean

    fun setNoAI(value: Boolean)

    fun spawn(player: Player): Boolean

    fun spawn()

    fun despawn(player: Player): Boolean

    fun despawn()

    fun remove()

    fun damage(event: EntityDamageEvent): Boolean

    fun knockBack(x: Float, z: Float, base: Float)

    override fun equals(other: Any?): Boolean
}