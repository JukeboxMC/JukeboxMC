package org.jukeboxmc.server.util

import com.google.gson.Gson
import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.extensions.fromJson
import org.jukeboxmc.api.item.TierType
import org.jukeboxmc.api.item.ToolType
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.server.block.BlockProperties
import java.awt.Color

class BlockAttributes {

    companion object {
        private val blockAttributes: MutableMap<Pair<Identifier, NbtMap>, BlockProperties> = mutableMapOf()
        private var additionalBlockData: MutableMap<String, MutableMap<String, String>> = mutableMapOf()

        init {
            val gson = Gson()
            val blockDataStream = ItemPalette::class.java.classLoader.getResourceAsStream("additional_block_data.json") ?: throw RuntimeException("additional_block_data.json was not found")
            val statelessProperties = hashMapOf<String, BlockPropertiesWithoutStates>()
            blockDataStream.reader().use {
                additionalBlockData = gson.fromJson<MutableMap<String, MutableMap<String, String>>>(it)
            }
            val inputStream = BlockAttributes::class.java.classLoader.getResourceAsStream("block_properties.json") ?: throw RuntimeException("The block properties were not found")
            inputStream.reader().use {
                val mutableMap = gson.fromJson<MutableMap<String, MutableMap<String, Any>>>(it)

                for (entry in mutableMap.entries) {
                    val identifier = Identifier.fromString(entry.key)
                    var toolType: ToolType = ToolType.NONE
                    var tierType: TierType = TierType.NONE
                    additionalBlockData[entry.key]?.let { map ->
                        map["toolType"]?.let { toolType = ToolType.valueOf(it) }
                        map["tierType"]?.let { tierType = TierType.valueOf(it) }
                    }
                    val blockProperties = BlockPropertiesWithoutStates(
                        Color.WHITE,
                        entry.value["blast_resistance"].toString().toFloat(),
                        entry.value["is_solid"].toString().toBoolean(),
                        entry.value["translucency"].toString().toFloat() > 0,
                        this.createBoundingBox(entry.value["aabb_collision"].toString()),
                        entry.value["has_block_entity"].toString().toBoolean(),
                        "Undefined",
                        entry.value["friction"].toString().toFloat(),
                        entry.value["hardness"].toString().toFloat(),
                        identifier,
                        entry.value["has_collision"].toString().toBoolean(),
                        toolType,
                        tierType
                    )
                    statelessProperties[identifier.getFullName()] = blockProperties
                }
            }

            for (blockDefinition in BlockPalette.getBlockDefinitions()) {
                val inputStream = BlockAttributes::class.java.classLoader.getResourceAsStream("block_properties.json") ?: throw RuntimeException("The block properties were not found")
                inputStream.reader().use {
                    val states = blockDefinition.state
                    val identifier = Identifier.fromString(blockDefinition.identifier)
                    val stateless = statelessProperties[identifier.getFullName()]!!
                    blockAttributes[Pair(identifier, states)] = BlockProperties(
                        stateless.getColor(),
                        stateless.getBlastResistance(),
                        blockDefinition.state,
                        stateless.isSolid(),
                        stateless.isTransparent(),
                        stateless.getBoundingBox(),
                        stateless.hasBlockEntity(),
                        stateless.getBlockEntityName(),
                        stateless.getFriction(),
                        stateless.getHardness(),
                        identifier,
                        stateless.hasCollision(),
                        stateless.getToolType(),
                        stateless.getTierType()
                    )
                }
            }
        }

        fun getBlockAttributes(): Collection<BlockProperties> {
            return blockAttributes.values
        }

        fun getBlockAttributes(identifier: Identifier, states: NbtMap): BlockProperties {
            val blockProperties = blockAttributes[Pair(identifier, states)]
            return blockProperties
                ?: throw NoSuchElementException("BlockProperties not found for $identifier and $states")
        }

        private fun createBoundingBox(aabbCollision: String): AxisAlignedBB {
            val data = aabbCollision.split(",")
            return AxisAlignedBB(
                data[0].toFloat(),
                data[1].toFloat(),
                data[2].toFloat(),
                data[3].toFloat(),
                data[4].toFloat(),
                data[5].toFloat()
            )
        }

        data class BlockPropertiesWithoutStates(
            private val color: Color,
            private val blastResistance: Float,
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
            fun getColor() = this.color
            fun getBlastResistance() = this.blastResistance
            fun isSolid() = this.solid
            fun isTransparent() = this.transparent
            fun getBoundingBox() = this.boundingBox
            fun hasBlockEntity() = this.hasBlockEntity
            fun getBlockEntityName() = this.blockEntityName
            fun getFriction() = this.friction
            fun getHardness() = this.hardness
            fun getIdentifier() = this.identifier
            fun hasCollision() = this.hasCollision
            fun getToolType() = this.toolType
            fun getTierType() = this.tierType
        }
    }
}