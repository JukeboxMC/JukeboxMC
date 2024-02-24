package org.jukeboxmc.server.entity

import org.apache.commons.math3.util.FastMath
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag
import org.cloudburstmc.protocol.bedrock.packet.*
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.event.entity.*
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.block.behavior.BlockLava
import org.jukeboxmc.server.block.behavior.BlockWater
import org.jukeboxmc.server.extensions.toJukeboxChunk
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.extensions.toJukeboxWorld
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Kaooot, LucGamesYT
 * @version 1.0
 */
open class JukeboxEntity(
    server: JukeboxServer = JukeboxServer.getInstance()
) : Entity {

    companion object {
        private var entityIdCounter: Long = 1
    }

    private val entityId: Long = entityIdCounter++
    private var age: Long = 0
    private var fireTicks: Long = 0
    private var isCollidedVertically = false
    private var isCollidedHorizontally = false
    private var isCollided = false
    private var onGround: Boolean = false
    private var removed: Boolean = false
    private var dead: Boolean = false
    private var boundingBox: AxisAlignedBB = AxisAlignedBB(0F, 0F, 0F, 0F, 0F, 0F)
    private var location: Location = server.getDefaultWorld().getSpawnLocation(Dimension.OVERWORLD)
    private var lastLocation: Location = server.getDefaultWorld().getSpawnLocation(Dimension.OVERWORLD)
    private var velocity: Vector = Vector(0, 0, 0)
    private var lastVelocity: Vector = Vector(0, 0, 0)
    private var metadata: Metadata = Metadata()
    private val spawnedFor: MutableSet<Long> = mutableSetOf()

    init {
        this.metadata.setInt(EntityDataTypes.PLAYER_INDEX, 0)
        this.metadata.setShort(EntityDataTypes.AIR_SUPPLY, 400.toShort())
        this.metadata.setShort(EntityDataTypes.AIR_SUPPLY_MAX, 400.toShort())
        this.metadata.setFloat(EntityDataTypes.SCALE, 1F)
        this.metadata.setFloat(EntityDataTypes.WIDTH, this.getWidth())
        this.metadata.setFloat(EntityDataTypes.HEIGHT, this.getHeight())
        this.metadata.setFlag(EntityFlag.HAS_GRAVITY, true)
        this.metadata.setFlag(EntityFlag.HAS_COLLISION, true)
        this.metadata.setFlag(EntityFlag.CAN_CLIMB, true)
        this.metadata.setFlag(EntityFlag.BREATHING, true)
        this.recalculateBoundingBox()
    }

    open fun tick(currentTick: Long) {
        this.age++
    }

    open fun createSpawnPacket(): BedrockPacket {
        val addEntityPacket = AddEntityPacket()
        addEntityPacket.runtimeEntityId = this.entityId
        addEntityPacket.uniqueEntityId = this.entityId
        addEntityPacket.identifier = this.getIdentifier().getFullName()
        addEntityPacket.position = this.location.add(0F, this.getEyeHeight(), 0F).toVector3f()
        addEntityPacket.motion = this.velocity.toVector3f()
        addEntityPacket.rotation = Vector2f.from(this.location.getPitch(), this.location.getYaw())
        addEntityPacket.metadata.putAll(this.metadata.getEntityDataMap())
        return addEntityPacket
    }

    open fun canCollideWith(entity: Entity): Boolean {
        return false
    }

    open fun onCollideWithPlayer(player: Player) {

    }

    private fun getMetaData(): Metadata {
        return this.metadata
    }

    private fun recalculateBoundingBox() {
        val height = this.getHeight() * this.getScale()
        val radius = this.getWidth() * this.getScale() / 2
        this.boundingBox.setBounds(
            this.location.getX() - radius,
            this.location.getY(),
            this.location.getZ() - radius,
            this.location.getX() + radius,
            this.location.getY() + height,
            this.location.getZ() + radius
        )
    }

    fun getMetadata(): Metadata {
        return this.metadata
    }

    fun getLastLocation(): Location {
        return this.lastLocation
    }

    fun setLastLocation(lastLocation: Location) {
        this.lastLocation = lastLocation
    }

    override fun getName(): String {
        return "Entity"
    }

    override fun getEntityId(): Long {
        return this.entityId
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:entity")
    }

    override fun getEntityType(): EntityType {
        return EntityType.UNKNOWN
    }

    override fun getBoundingBox(): AxisAlignedBB {
        return this.boundingBox
    }

    override fun getVelocity(): Vector {
        return this.velocity
    }

    override fun setVelocity(velocity: Vector) {
        val entityVelocityEvent = EntityVelocityEvent(this, velocity)
        JukeboxServer.getInstance().getPluginManager().callEvent(entityVelocityEvent)
        if (entityVelocityEvent.isCancelled()) {
            return
        }
        this.velocity = entityVelocityEvent.getVelocity()
        val entityVelocityPacket = SetEntityMotionPacket()
        entityVelocityPacket.runtimeEntityId = entityVelocityEvent.getEntity().getEntityId()
        entityVelocityPacket.motion = entityVelocityEvent.getVelocity().toVector3f()
        this.getWorld().sendDimensionPacket(this.getDimension(), entityVelocityPacket)
    }

    override fun setMotion(motion: Vector) {
        this.velocity = motion
    }

    override fun getLocation(): Location {
        return this.location
    }

    override fun setLocation(location: Location) {
        this.location = location
        this.recalculateBoundingBox()
    }

    override fun getWorld(): JukeboxWorld {
        return this.location.getWorld().toJukeboxWorld()
    }

    override fun getX(): Float {
        return this.location.getX()
    }

    override fun setX(x: Float) {
        this.location.setX(x)
    }

    override fun getY(): Float {
        return this.location.getY()
    }

    override fun setY(y: Float) {
        this.location.setY(y)
    }

    override fun getZ(): Float {
        return this.location.getZ()
    }

    override fun setZ(z: Float) {
        this.location.setZ(z)
    }

    override fun getBlockX(): Int {
        return this.location.getBlockX()
    }

    override fun getBlockY(): Int {
        return this.location.getBlockY()
    }

    override fun getBlockZ(): Int {
        return this.location.getBlockZ()
    }

    override fun getYaw(): Float {
        return this.location.getYaw()
    }

    override fun setYaw(yaw: Float) {
        this.location.setYaw(yaw)
    }

    override fun getPitch(): Float {
        return this.location.getPitch()
    }

    override fun setPitch(pitch: Float) {
        this.location.setPitch(pitch)
    }

    override fun getChunkX(): Int {
        return getBlockX() shr 4
    }

    override fun getChunkZ(): Int {
        return getBlockZ() shr 4
    }

    override fun getChunk(): Chunk? {
        return this.location.getWorld()
            .getChunk(this.location.getChunkX(), this.location.getChunkZ(), this.location.getDimension())
    }

    override fun getLoadedChunk(): Chunk? {
        return this.location.getWorld()
            .getLoadedChunk(this.location.getChunkX(), this.location.getChunkZ(), this.location.getDimension())
    }

    override fun getDimension(): Dimension {
        return this.location.getDimension()
    }

    override fun getEyeHeight(): Float {
        return this.getHeight() / 2 + 0.1f
    }

    override fun getWidth(): Float {
        return 0F
    }

    override fun getHeight(): Float {
        return 0F
    }

    override fun isClosed(): Boolean {
        return false
    }

    override fun isOnGround(): Boolean {
        return this.onGround
    }

    fun setOnGround(onGround: Boolean) {
        this.onGround = onGround
    }

    override fun isDead(): Boolean {
        return this.dead
    }

    fun setDead(dead: Boolean) {
        this.dead = dead
    }

    override fun getAge(): Long {
        return this.age
    }

    override fun getDirection(): Direction {
        var rotation = (this.location.getYaw() % 360).toDouble()
        if (rotation < 0) {
            rotation += 360.0
        }
        return if (45 <= rotation && rotation < 135) {
            Direction.WEST
        } else if (135 <= rotation && rotation < 225) {
            Direction.NORTH
        } else if (225 <= rotation && rotation < 315) {
            Direction.EAST
        } else {
            Direction.SOUTH
        }
    }

    override fun getMaxAirSupply(): Short {
        return this.metadata.getShort(EntityDataTypes.AIR_SUPPLY)
    }

    override fun setMaxAirSupply(value: Short) {
        if (value != this.getMaxAirSupply()) {
            this.updateMetadata(this.metadata.setShort(EntityDataTypes.AIR_SUPPLY, value))
        }
    }

    override fun getScale(): Float {
        return metadata.getFloat(EntityDataTypes.SCALE)
    }

    override fun setScale(value: Float) {
        if (value != this.getScale()) {
            this.updateMetadata(this.metadata.setFloat(EntityDataTypes.SCALE, value))
        }
    }

    override fun hasCollision(): Boolean {
        return this.metadata.getFlag(EntityFlag.HAS_COLLISION)
    }

    override fun setCollision(value: Boolean) {
        if (value != this.hasCollision()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.HAS_COLLISION, value))
        }
    }

    override fun hasGravity(): Boolean {
        return this.metadata.getFlag(EntityFlag.HAS_GRAVITY)
    }

    override fun setGravity(value: Boolean) {
        if (value != this.hasGravity()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.HAS_GRAVITY, value))
        }
    }

    override fun getNameTag(): String {
        return this.metadata.getString(EntityDataTypes.NAME)
    }

    override fun setNameTag(value: String) {
        if (value != this.getNameTag()) {
            this.updateMetadata(this.metadata.setString(EntityDataTypes.NAME, value))
        }
    }

    override fun isNameTagVisible(): Boolean {
        return this.metadata.getFlag(EntityFlag.CAN_SHOW_NAME)
    }

    override fun setNameTagVisible(value: Boolean) {
        if (value != this.isNameTagVisible()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.CAN_SHOW_NAME, value))
        }
    }

    override fun isNameTagAlwaysVisible(): Boolean {
        return this.metadata.getFlag(EntityFlag.ALWAYS_SHOW_NAME)
    }

    override fun setNameTagAlwaysVisible(value: Boolean) {
        if (value != this.isNameTagAlwaysVisible()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.ALWAYS_SHOW_NAME, value))
        }
    }

    override fun canClimb(): Boolean {
        return this.metadata.getFlag(EntityFlag.CAN_CLIMB)
    }

    override fun setCanClimb(value: Boolean) {
        if (value != this.canClimb()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.CAN_CLIMB, value))
        }
    }

    override fun isInvisible(): Boolean {
        return this.metadata.getFlag(EntityFlag.INVISIBLE)
    }

    override fun setInvisible(value: Boolean) {
        if (value != this.isInvisible()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.INVISIBLE, value))
        }
    }

    override fun getFireTicks(): Long {
        return this.fireTicks
    }

    fun setFireTicks(fireTicks: Long) {
        this.fireTicks = fireTicks
    }

    override fun isOnFire(): Boolean {
        return this.metadata.getFlag(EntityFlag.ON_FIRE)
    }

    override fun setOnFire(value: Boolean) {
        if (value != this.isOnFire()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.ON_FIRE, value))
        }
    }

    override fun setOnFire(value: Long, timeUnit: TimeUnit) {
        val newFireTicks = (timeUnit.toMillis(value) / 50).toInt()
        if (newFireTicks > this.fireTicks) {
            this.fireTicks = newFireTicks.toLong()
            this.setOnFire(true)
        } else if (newFireTicks == 0) {
            this.fireTicks = 0
            this.setOnFire(false)
        }
    }

    override fun hasNoAI(): Boolean {
        return this.metadata.getFlag(EntityFlag.NO_AI)
    }

    override fun setNoAI(value: Boolean) {
        if (value != this.isOnFire()) {
            this.updateMetadata(this.metadata.setFlag(EntityFlag.NO_AI, value))
        }
    }

    override fun spawn(player: Player): Boolean {
        if (!this.spawnedFor.contains(player.getEntityId()) && player.isChunkLoaded(
                this.getChunkX(),
                this.getChunkZ()
            )
        ) {
            val entitySpawnEvent = EntitySpawnEvent(this)
            JukeboxServer.getInstance().getPluginManager().callEvent(entitySpawnEvent)
            if (entitySpawnEvent.isCancelled()) return false
            val chunk = this.getLoadedChunk() ?: return false
            chunk.toJukeboxChunk().addEntity(this)
            this.getWorld().addEntity(this)
            val entity = entitySpawnEvent.getEntity() as JukeboxEntity
            player.toJukeboxPlayer().sendPacket(entity.createSpawnPacket())
            this.spawnedFor.add(player.getEntityId())
            return true
        }
        return false
    }

    override fun spawn() {
        for (player in this.getWorld().getPlayers()) {
            this.spawn(player)
        }
    }

    override fun despawn(player: Player): Boolean {
        if (this.spawnedFor.contains(player.getEntityId())) {
            val entityDespawnEvent = EntityDespawnEvent(this)
            JukeboxServer.getInstance().getPluginManager().callEvent(entityDespawnEvent)
            if (entityDespawnEvent.isCancelled()) return false
            val removeEntityPacket = RemoveEntityPacket()
            removeEntityPacket.uniqueEntityId = entityDespawnEvent.getEntity().getEntityId()
            player.toJukeboxPlayer().sendPacket(removeEntityPacket)
            this.spawnedFor.remove(player.getEntityId())
            return true
        }
        return false
    }

    override fun despawn() {
        for (player in this.getWorld().getPlayers()) {
            this.despawn(player)
        }
    }

    fun spawnedPlayers(): List<JukeboxPlayer> {
        return this.spawnedFor.map { this.getWorld().getEntityById(it) as JukeboxPlayer }.toList()
    }

    override fun remove() {
        if (this is JukeboxPlayer && !this.isSpawned()) return

        this.despawn()

        this.getWorld().removeEntity(this)

        this.dead = true
        this.removed = true

        val chunk = this.getLoadedChunk() ?: return
        chunk.toJukeboxChunk().removeEntity(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Entity) return false
        return other.getEntityId() == this.entityId
    }

    fun updateMetadata(metadata: Metadata) {
        val setEntityDataPacket = SetEntityDataPacket()
        setEntityDataPacket.runtimeEntityId = this.entityId
        setEntityDataPacket.metadata.putAll(metadata.getEntityDataMap())
        setEntityDataPacket.tick = JukeboxServer.getInstance().getCurrentTick()
        JukeboxServer.getInstance().broadcastPacket(setEntityDataPacket)
    }

    fun updateMetadata() {
        val setEntityDataPacket = SetEntityDataPacket()
        setEntityDataPacket.runtimeEntityId = entityId
        setEntityDataPacket.metadata.putAll(metadata.getEntityDataMap())
        setEntityDataPacket.tick = JukeboxServer.getInstance().getCurrentTick()
        JukeboxServer.getInstance().broadcastPacket(setEntityDataPacket)
    }

    fun isInWater(): Boolean = this.isInLiquid(true)

    fun isInLava(): Boolean = this.isInLiquid(false)

    fun isInLiquid() = this.isInWater() || this.isInLava()

    private fun isInLiquid(water: Boolean): Boolean {
        val eyeLocation: Vector = location.add(0F, getEyeHeight(), 0F)
        val loadedChunk =
            getWorld().getLoadedChunk(eyeLocation.getChunkX(), eyeLocation.getChunkZ(), location.getDimension())
                ?: return false
        val block: Block =
            loadedChunk.getBlock(eyeLocation.getBlockX(), eyeLocation.getBlockY(), eyeLocation.getBlockZ(), 0)
        if ((!water && (block.getType() == BlockType.LAVA || block.getType() == BlockType.FLOWING_LAVA)) || (water && (block.getType() == BlockType.WATER || block.getType() == BlockType.FLOWING_WATER))) {
            return eyeLocation.getY() < (block.getLocation()
                .getY() + 1 + ((if (!water) (block as BlockLava).getLiquidDepth() else (block as BlockWater).getLiquidDepth()) - 0.12))
        }
        return false
    }

    fun isOnLadder(): Boolean {
        val location = getLocation()
        val loadedChunk =
            location.getWorld().getLoadedChunk(location.getChunkX(), location.getChunkZ(), location.getDimension())
                ?: return false
        val block = loadedChunk.getBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0)
        return block.getType() == BlockType.LADDER
    }

    override fun damage(event: EntityDamageEvent): Boolean {
        if (this.isDead()) return false
        if (event is EntityDamageByEntityEvent) {
            val damager = event.getDamager()
            if (damager is Player && damager.getGameMode() == GameMode.SPECTATOR) {
                return false
            }
        }
        JukeboxServer.getInstance().getPluginManager().callEvent(event)
        if (!event.isCancelled() && event is EntityDamageByEntityEvent) {
            val damager = event.getDamager()
            val farAway = damager.getLocation().distanceSquared(this.getLocation()) > (200 * 200)
            var dx: Float = if (farAway) ((Math.random() - Math.random()).toFloat()) else damager.getX() - this.getX()
            var dz: Float = if (farAway) ((Math.random() - Math.random()).toFloat()) else (damager.getZ() - this.getZ())
            while (dx * dx + dz * dz < 1.0E-4) {
                dx = ((Math.random() - Math.random()) * 0.01).toFloat()
                dz = ((Math.random() - Math.random()) * 0.01).toFloat()
            }
            var knockbackLevel = 0
            if (damager is EntityHuman) {
                val knockback = damager.getInventory().getItemInHand().getEnchantment(EnchantmentType.KNOCKBACK)
                if (knockback != null) {
                    knockbackLevel += knockback.getLevel()
                }
            }
            if (knockbackLevel > 0) {
                this.knockBack(
                    sin(damager.getYaw() * 0.017453292f),
                    (-cos(damager.getYaw() * 0.017453292f)),
                    knockbackLevel * 0.5F
                )
            } else {
                this.knockBack(dx, dz, 0.4F)
            }
        }
        return !event.isCancelled()
    }

    override fun knockBack(x: Float, z: Float, base: Float) {
        var knockBackResistance = 0.0f
        // each piece of netherite armor adds 10 percent knockback resistance to its wearer
        if (this is EntityHuman) {
            val armorContents = this.getArmorInventory().getContents()
            for (armorContent in armorContents) {
                if (armorContent.getTierType() == TierType.NETHERITE) {
                    knockBackResistance += 0.1f
                }
            }
        }
        var b = base
        b *= 1.0f - knockBackResistance
        if (b > 0.0) {
            val velocity = this.velocity
            val normalized = Vector(x, 0.0f, z).normalize().multiply(b, b, b)
            this.setVelocity(
                Vector(
                    velocity.getX() / 2.0f - normalized.getX(),
                    if (this.onGround) 0.4f.coerceAtMost((velocity.getY() / 2.0f + b)) else velocity.getY(),
                    velocity.getZ() / 2.0f - normalized.getZ()
                )
            )
        }
    }

    fun move(velocity: Vector) {
        if (velocity.getX() == 0f && velocity.getY() == 0f && velocity.getZ() == 0f) return

        val movX = velocity.getX()
        val movY = velocity.getY()
        val movZ = velocity.getZ()

        val list = this.location.getWorld()
            .getCollisionCubes(this.boundingBox.addCoordinates(velocity.getX(), velocity.getY(), velocity.getZ()), this)
        for (bb in list) {
            velocity.setY(bb.calculateYOffset(this.boundingBox, velocity.getY()))
        }
        this.boundingBox.offset(0.0f, velocity.getY(), 0.0f)

        for (bb in list) {
            velocity.setX(bb.calculateXOffset(this.boundingBox, velocity.getX()))
        }
        this.boundingBox.offset(velocity.getX(), 0.0f, 0.0f)

        for (bb in list) {
            velocity.setZ(bb.calculateZOffset(this.boundingBox, velocity.getZ()))
        }
        this.boundingBox.offset(0.0f, 0.0f, velocity.getZ())

        this.location.setX((this.boundingBox.getMinX() + this.boundingBox.getMaxX()) / 2)
        this.location.setY(this.boundingBox.getMinY())
        this.location.setZ((this.boundingBox.getMinZ() + this.boundingBox.getMaxZ()) / 2)

        val fromChunk = this.lastLocation.getChunk()?.toJukeboxChunk()
        val toChunk = this.location.getChunk()?.toJukeboxChunk()

        if (fromChunk != null && toChunk != null) {
            if (fromChunk.getX() != toChunk.getX() || fromChunk.getZ() != toChunk.getZ()) {
                fromChunk.removeEntity(this)
                toChunk.addEntity(this)
            }
        }

        this.isCollidedVertically = movY != velocity.getY()
        this.isCollidedHorizontally = movX != velocity.getX() || movZ != velocity.getZ()
        this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically
        this.onGround = movY != velocity.getY() && movY < 0

        if (movX != velocity.getX()) {
            velocity.setX(0F)
        }

        if (movY != velocity.getY()) {
            velocity.setY(0F)
        }

        if (movZ != velocity.getZ()) {
            velocity.setZ(0F)
        }
    }


    fun checkObstruction(x: Float, y: Float, z: Float) {
        if (location.getWorld().getCollisionCubes(this.boundingBox, this).isEmpty()) {
            return
        }
        val i = FastMath.floor(x.toDouble()).toInt()
        val j = FastMath.floor(y.toDouble()).toInt()
        val k = FastMath.floor(z.toDouble()).toInt()
        val diffX = x - i
        val diffY = y - j
        val diffZ = z - k
        if (!this.getWorld().getBlock(i, j, k, 0, this.location.getDimension()).isTransparent()) {
            val flag = this.getWorld().getBlock(i - 1, j, k, 0, this.location.getDimension()).isTransparent()
            val flag1 = this.getWorld().getBlock(i + 1, j, k, 0, this.location.getDimension()).isTransparent()
            val flag2 = this.getWorld().getBlock(i, j - 1, k, 0, this.location.getDimension()).isTransparent()
            val flag3 = this.getWorld().getBlock(i, j + 1, k, 0, this.location.getDimension()).isTransparent()
            val flag4 = this.getWorld().getBlock(i, j, k - 1, 0, this.location.getDimension()).isTransparent()
            val flag5 = this.getWorld().getBlock(i, j, k + 1, 0, this.location.getDimension()).isTransparent()
            var direction = -1
            var limit = 9999.0
            if (flag) {
                limit = diffX.toDouble()
                direction = 0
            }
            if (flag1 && 1 - diffX < limit) {
                limit = (1 - diffX).toDouble()
                direction = 1
            }
            if (flag2 && diffY < limit) {
                limit = diffY.toDouble()
                direction = 2
            }
            if (flag3 && 1 - diffY < limit) {
                limit = (1 - diffY).toDouble()
                direction = 3
            }
            if (flag4 && diffZ < limit) {
                limit = diffZ.toDouble()
                direction = 4
            }
            if (flag5 && 1 - diffZ < limit) {
                direction = 5
            }
            val force = Random().nextFloat() * 0.2f + 0.1f
            if (direction == 0) {
                this.velocity = this.velocity.subtract(force, 0F, 0F)
                return
            }
            if (direction == 1) {
                this.velocity.setX(force)
                return
            }
            if (direction == 2) {
                this.velocity = this.velocity.subtract(0F, force, 0F)
                return
            }
            if (direction == 3) {
                this.velocity.setY(force)
                return
            }
            if (direction == 4) {
                this.velocity = this.velocity.subtract(0F, 0F, force)
                return
            }
            if (direction == 5) {
                this.velocity.setZ(force)
            }
        }
    }

    fun updateMovement() {
        val diffPosition =
            (this.location.getX() - this.lastLocation.getX()) * (this.location.getX() - this.lastLocation.getX()) + (this.location.getY() - this.lastLocation.getY()) * (this.location.getY() - this.lastLocation.getY()) + (this.location.getZ() - this.lastLocation.getZ()) * (this.location.getZ() - this.lastLocation.getZ())
        val diffRotation =
            (this.location.getYaw() - this.lastLocation.getYaw()) * (this.location.getYaw() - this.lastLocation.getYaw()) + (this.location.getPitch() - this.lastLocation.getPitch()) * (this.location.getPitch() - this.lastLocation.getPitch())
        val diffMotion: Float =
            (this.velocity.getX() - this.lastVelocity.getX()) * (this.velocity.getX() - this.lastVelocity.getX()) + (this.velocity.getY() - this.lastVelocity.getY()) * (this.velocity.getY() - this.lastVelocity.getY()) + (this.velocity.getZ() - this.lastVelocity.getZ()) * (this.velocity.getZ() - this.lastVelocity.getZ())
        if (diffPosition > 0.0001 || diffRotation > 1.0) {
            this.lastLocation.setX(this.location.getX())
            this.lastLocation.setY(this.location.getY())
            this.lastLocation.setZ(this.location.getZ())
            this.lastLocation.setYaw(this.location.getYaw())
            this.lastLocation.setPitch(this.location.getPitch())
            sendEntityMovePacket(
                Location(
                    this.location.getWorld(),
                    this.location.getX(),
                    this.location.getY() + 0,
                    this.location.getZ(),
                    this.location.getYaw(),
                    this.location.getPitch(),
                    this.location.getDimension()
                ),
                this.onGround
            )
        }
        if (diffMotion > 0.0025 || diffMotion > 0.0001 && getVelocity().squaredLength() <= 0.0001) {
            this.lastVelocity.setX(velocity.getX())
            this.lastVelocity.setY(velocity.getY())
            this.lastVelocity.setZ(velocity.getZ())
            this.setVelocity(velocity)
        }
    }

    fun sendEntityMovePacket(location: Location, onGround: Boolean) {
        val moveEntityAbsolutePacket = MoveEntityAbsolutePacket()
        moveEntityAbsolutePacket.runtimeEntityId = entityId
        moveEntityAbsolutePacket.isTeleported = false
        moveEntityAbsolutePacket.isOnGround = onGround
        moveEntityAbsolutePacket.position = location.toVector3f()
        moveEntityAbsolutePacket.rotation = Vector3f.from(location.getPitch(), location.getYaw(), location.getYaw())
        JukeboxServer.getInstance().broadcastPacket(moveEntityAbsolutePacket)
    }

    override fun hashCode(): Int {
        return this.entityId.hashCode()
    }
}