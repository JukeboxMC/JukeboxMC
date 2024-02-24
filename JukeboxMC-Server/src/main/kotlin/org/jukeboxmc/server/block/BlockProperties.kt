package org.jukeboxmc.server.block

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.math.AxisAlignedBB
import java.awt.Color

class BlockProperties(
        private val color: Color,
        private val blastResistance: Float,
        private val states: NbtMap,
        private val solid: Boolean,
        private val transparent: Boolean,
        private val boundingBox: AxisAlignedBB,
        private val hasBlockEntity: Boolean,
        private val blockEntityName: String,
        private val friction: Float,
        private val hardness: Float,
        private val identifier: Identifier,
        private val hasCollision: Boolean,
        private var toolType: ToolType = ToolType.NONE,
        private var tierType: TierType = TierType.NONE
) {

    fun getColor(): Color {
        return this.color
    }

    fun getBlastResistance(): Float {
        return this.blastResistance
    }

    fun getStates(): NbtMap {
        return this.states
    }

    fun isSolid(): Boolean {
        return this.solid
    }

    fun isTransparent(): Boolean {
        return this.transparent
    }

    fun getBoundingBox(): AxisAlignedBB {
        return this.boundingBox
    }

    fun hasBlockEntity(): Boolean {
        return this.hasBlockEntity
    }

    fun getBlockEntityName(): String {
        return this.blockEntityName
    }

    fun getFriction(): Float {
        return this.friction
    }

    fun getHardness(): Float {
        return this.hardness
    }

    fun getIdentifier(): Identifier {
        return this.identifier
    }

    fun hasCollision(): Boolean {
        return this.hasCollision
    }

    fun getToolType(): ToolType {
        return this.toolType
    }

    fun getTierType(): TierType {
        return this.tierType
    }

    override fun toString(): String {
        return "BlockProperties(color=$color, blastResistance=$blastResistance, states=$states, solid=$solid, transparent=$transparent, boundingBox=$boundingBox, hasBlockEntity=$hasBlockEntity, blockEntityName='$blockEntityName', friction=$friction, hardness=$hardness, identifier=$identifier, hasCollision=$hasCollision)"
    }


}