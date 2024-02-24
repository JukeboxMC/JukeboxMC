package org.jukeboxmc.server.util

import com.google.gson.Gson
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.nbt.NbtUtils
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
            val blockDataStream = ItemPalette::class.java.classLoader.getResourceAsStream("additional_block_data.json")
                    ?: throw RuntimeException("additional_block_data.json was not found")
            blockDataStream.reader().use {
                additionalBlockData = gson.fromJson<MutableMap<String, MutableMap<String, String>>>(it)
            }
            val inputStream =
                    BlockAttributes::class.java.classLoader.getResourceAsStream("block_attributes.nbt")
                            ?: throw RuntimeException("The block palette was not found")
            NbtUtils.createGZIPReader(inputStream).use { nbtInputStream ->
                val blocks = (nbtInputStream.readTag() as NbtMap).getList("block", NbtType.COMPOUND)
                for (compound in blocks) {
                    val identifier = Identifier.fromString(compound.getString("name"))
                    val states = compound.getCompound("states")
                    var toolType: ToolType = ToolType.NONE
                    var tierType: TierType = TierType.NONE
                    additionalBlockData[compound.getString("name")]?.let { map ->
                        map["toolType"]?.let { toolType = ToolType.valueOf(it) }
                        map["tierType"]?.let { tierType = TierType.valueOf(it) }
                    }
                    val blockProperties = BlockProperties(
                            Color(
                                    compound.getCompound("color").getInt("r"),
                                    compound.getCompound("color").getInt("g"),
                                    compound.getCompound("color").getInt("b"),
                                    compound.getCompound("color").getInt("a")
                            ),
                            compound.getFloat("explosionResistance"),
                            states,
                            compound.getBoolean("isSolid"),
                            compound.getFloat("translucency") > 0,
                            this.createBoundingBox(compound),
                            compound.getBoolean("hasBlockEntity"),
                            compound.getString("blockEntityName"),
                            compound.getFloat("friction"),
                            compound.getFloat("hardness"),
                            identifier,
                            compound.getBoolean("hasCollision"),
                            toolType,
                            tierType
                    )
                    blockAttributes[Pair(identifier, states)] = blockProperties
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

        private fun createBoundingBox(compound: NbtMap): AxisAlignedBB {
            val aabbCollision = compound.getString("aabbCollision")
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
    }
}