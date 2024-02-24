package org.jukeboxmc.server.util

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleBlockDefinition
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.server.block.BlockRegistry
import java.util.*
import java.util.function.Predicate

/**
 * @author Kaooot
 * @version 1.0
 */
class BlockPalette {

    companion object {
        private val blockDefinitions = arrayListOf<SimpleBlockDefinition>()
        private val blockStateFromNetworkId: MutableMap<Int, NbtMap> = mutableMapOf()
        private val statesFromNetworkId: MutableMap<Int, NbtMap> = mutableMapOf()
        private val blockCache: MutableMap<BlockData, Block> = mutableMapOf()
        private val blockNbt: MutableList<NbtMap> = mutableListOf()

        init {
            val stream =
                BlockPalette::class.java.classLoader.getResourceAsStream("block_palette.nbt") ?: throw RuntimeException(
                    "The block palette was not found"
                )

            stream.use {
                NbtUtils.createGZIPReader(it).use { nbtInputStream ->
                    val nbtMap = nbtInputStream.readTag() as NbtMap
                    val blocks = nbtMap.getList("blocks", NbtType.COMPOUND)
                    blockNbt.addAll(blocks)

                    for (blockMap in blocks) {
                        val networkId = blockMap.getInt("network_id")
                        this.blockStateFromNetworkId[networkId] = blockMap.getCompound("states")
                        this.statesFromNetworkId[networkId] = blockMap
                        this.blockDefinitions.add(
                            SimpleBlockDefinition(
                                blockMap.getString("name"),
                                networkId,
                                blockMap.getCompound("states")
                            )
                        )
                    }
                }
            }
        }

        fun getBlockDefinitions(): List<SimpleBlockDefinition> = this.blockDefinitions

        fun getNetworkId(blockMap: NbtMap): Int {
            for (networkId in this.statesFromNetworkId.keys) {
                if (this.statesFromNetworkId[networkId] == blockMap) {
                    return networkId
                }
            }
            throw NullPointerException("Block was not found")
        }

        fun searchBlocks(predicate: Predicate<NbtMap>): List<NbtMap> {
            val blocks: MutableList<NbtMap> = ArrayList()
            for (nbtMap in this.statesFromNetworkId.values) {
                if (predicate.test(nbtMap)) {
                    blocks.add(nbtMap)
                }
            }
            return Collections.unmodifiableList(blocks)
        }

        fun getBlockNbt(networkId: Int): NbtMap {
            for (it in blockNbt) {
                if (it.getInt("network_id") == networkId) {
                    return it
                }
            }
            return NbtMap.EMPTY
        }

        fun getBlockByNBT(nbtMap: NbtMap): Block {
            val identifier: Identifier = Identifier.fromString(nbtMap.getString("name"))
            val blockStates = nbtMap.getCompound("states")
            val blockData = BlockData(identifier, blockStates)
            if (blockCache.containsKey(blockData)) {
                return blockCache[blockData]!!
            }
            val block = Block.create(BlockRegistry.getBlockType(identifier), blockStates)
            blockCache[blockData] = block
            return block
        }

        class BlockData(private val identifier: Identifier, private val blockStates: NbtMap) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (this.javaClass != other?.javaClass) return false
                other as BlockData
                if (this.identifier != other.identifier) return false
                return this.blockStates == other.blockStates
            }
            override fun hashCode(): Int {
                var result = this.identifier.hashCode()
                result = 31 * result + this.blockStates.hashCode()
                return result
            }
        }

    }
}