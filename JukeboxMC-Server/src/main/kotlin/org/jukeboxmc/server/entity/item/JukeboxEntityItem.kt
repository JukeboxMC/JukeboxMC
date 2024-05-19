package org.jukeboxmc.server.entity.item

import org.cloudburstmc.protocol.bedrock.packet.AddItemEntityPacket
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.TakeItemEntityPacket
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.entity.item.EntityItem
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.event.entity.ItemDespawnEvent
import org.jukeboxmc.api.event.player.PlayerPickupItemEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.entity.JukeboxEntity
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.inventory.ContainerInventory
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.floor

class JukeboxEntityItem : JukeboxEntity(), EntityItem {

    private var item: Item = Item.create(ItemType.AIR)
    private var pickupDelay: Long = 0
    private var thrower: EntityHuman? = null
    private var reset: Boolean = false

    override fun tick(currentTick: Long) {
        super.tick(currentTick)
        if (this.isClosed()) return
        if (this.hasNoAI()) return
        val velocity = this.getVelocity()

        velocity.setY(this.getVelocity().getY() - 0.04F)

        this.checkObstruction(this.getLocation().getX(), (getBoundingBox().getMinY() + getBoundingBox().getMaxY()) / 2.0F, this.getLocation().getZ())
        this.move(velocity)

        var friction = 0.98f
        if (this.isOnGround() && (abs(velocity.getX()) > 0.00001 || abs(velocity.getZ()) > 0.00001)) {
            friction = this.getWorld().getBlock(this.getBlockX(), floor(this.getBoundingBox().getMinY()).toInt() - 1, this.getBlockZ()).getFriction() * 0.96F
        }

        velocity.setX(velocity.getX() * friction)
        velocity.setY(velocity.getY() * 0.98F)
        velocity.setZ(velocity.getZ() * friction)

        if (this.isOnGround()) {
            velocity.setY(velocity.getY() * -0.5F)
        }

        this.setMotion(velocity)
        this.updateMovement()

        if (this.isCollided() && !this.reset && velocity.squaredLength() < 0.01f) {
            this.setVelocity(Vector.zero())
            this.reset = true
        }

        if (this.getAge() >= TimeUnit.MINUTES.toMillis(5) / 50) {
            val itemDespawnEvent = ItemDespawnEvent(this)
            JukeboxServer.getInstance().getPluginManager().callEvent(itemDespawnEvent)
            if (itemDespawnEvent.isCancelled()) return
            this.remove()
        }
    }

    override fun getName(): String {
        return "EntityItem"
    }

    override fun getEntityType(): EntityType {
        return EntityType.ITEM
    }

    override fun getHeight(): Float {
        return 0.25F
    }

    override fun getWidth(): Float {
        return 0.25F
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:item")
    }

    override fun createSpawnPacket(): BedrockPacket {
        val addItemEntityPacket = AddItemEntityPacket()
        addItemEntityPacket.runtimeEntityId = this.getEntityId()
        addItemEntityPacket.uniqueEntityId = this.getEntityId()
        addItemEntityPacket.itemInHand = this.item.toJukeboxItem().toItemData()
        addItemEntityPacket.position = this.getLocation().toVector3f()
        addItemEntityPacket.motion = this.getVelocity().toVector3f()
        addItemEntityPacket.metadata.putAll(this.getMetadata().getEntityDataMap())
        return addItemEntityPacket
    }

    override fun onCollideWithPlayer(player: Player) {
        if (JukeboxServer.getInstance().getCurrentTick() > pickupDelay && !isClosed() && !player.isDead()) {
            val playerPickupItemEvent = PlayerPickupItemEvent(player, item)
            JukeboxServer.getInstance().getPluginManager().callEvent(playerPickupItemEvent)
            if (playerPickupItemEvent.isCancelled() || !player.getInventory().canAddItem(playerPickupItemEvent.getItem())) {
                return
            }
            val takeItemEntityPacket = TakeItemEntityPacket()
            takeItemEntityPacket.runtimeEntityId = player.getEntityId()
            takeItemEntityPacket.itemRuntimeEntityId = this.getEntityId()
            JukeboxServer.getInstance().broadcastPacket(takeItemEntityPacket)
            this.remove()
            player.getInventory().addItem(playerPickupItemEvent.getItem())
            (player.getInventory() as ContainerInventory).sendContents(player.toJukeboxPlayer())
        }
    }

    override fun getItem(): Item {
        return this.item.clone()
    }

    override fun setItem(item: Item) {
        this.item = item
    }

    override fun getPickupDelay(): Long {
        return this.pickupDelay
    }

    override fun setPickupDelay(pickupDelay: Long, timeUnit: TimeUnit) {
        this.pickupDelay = JukeboxServer.getInstance().getCurrentTick() + timeUnit.toMillis(pickupDelay) / 50
    }

    override fun getThrower(): EntityHuman? {
        return this.thrower
    }

    override fun setThrower(thrower: EntityHuman?) {
        this.thrower = thrower
    }
}