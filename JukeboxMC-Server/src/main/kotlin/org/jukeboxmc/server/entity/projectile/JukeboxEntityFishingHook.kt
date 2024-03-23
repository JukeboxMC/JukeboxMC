package org.jukeboxmc.server.entity.projectile

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.entity.passive.JukeboxEntityHuman

class JukeboxEntityFishingHook : JukeboxEntityProjectile() {

    private var reset = false

    override fun tick(currentTick: Long) {
        if (this.isClosed() || this.isDead()) return
        super.tick(currentTick)
        val shooter = this.getShooter() ?: return


        if (shooter is JukeboxEntityHuman) {
            if (shooter.isDead() || shooter.getInventory().getItemInHand().getType() != ItemType.FISHING_ROD) {
                this.remove()
                shooter.setEntityFishingHook(null)
                return
            }
        }

        if (this.isCollided() && this.isInLiquid()) {
            val vector = Vector(0f, 0.1f, 0f)
            if (this.getVelocity() != vector) {
                this.setVelocity(vector)
            }
        } else if (this.isCollided()) {
            if (!this.reset && this.getVelocity().squaredLength() < 0.0025) {
                this.setVelocity(Vector.zero())
                this.reset = true
            }
        }
    }

    override fun getName(): String {
        return "Fishing Hook"
    }

    override fun getWidth(): Float {
        return 0.2f
    }

    override fun getHeight(): Float {
        return 0.2f
    }

    override fun getGravity(): Float {
        return 0.05f
    }

    override fun getEntityType(): EntityType {
        return EntityType.FISHING_HOOK
    }

    override fun getIdentifier(): Identifier {
        return Identifier.fromString("minecraft:fishing_hook")
    }

    override fun createSpawnPacket(): BedrockPacket {
        this.getMetadata().setLong(EntityDataTypes.OWNER_EID, this.getShooter()?.getEntityId() ?: -1)
        return super.createSpawnPacket()
    }
}