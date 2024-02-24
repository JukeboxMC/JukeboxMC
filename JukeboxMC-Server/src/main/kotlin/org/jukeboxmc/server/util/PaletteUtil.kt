package org.jukeboxmc.server.util

import com.google.gson.Gson
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.jukeboxmc.api.extensions.fromJson
import org.jukeboxmc.server.block.RuntimeBlockDefinition
import java.io.ByteArrayInputStream
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
class PaletteUtil {

    companion object {
        private val biomeDefinitions: NbtMap
        private val entityIdentifiers: NbtMap
        private val creativeItems = mutableListOf<ItemData>()
        private val gson = Gson()

        init {
            this.biomeDefinitions = this.readNbt("biome_definitions.dat")
            this.entityIdentifiers = this.readNbt("entity_identifiers.dat")

            val stream = PaletteUtil::class.java.classLoader.getResourceAsStream("creative_items.json") ?: throw RuntimeException("Could not find creative items")

            stream.reader().use {
                val itemEntries = this.gson.fromJson<Map<String, List<Map<String, Any>>>>(it)
                var netIdCounter = 0

                for (itemEntry in itemEntries["items"]!!) {
                    val identifier = itemEntry["id"].toString()
                    var blockRuntimeId = 0

                    if (itemEntry.containsKey("block_state_b64")) {
                        blockRuntimeId = this.runtimeByBlockStateBase64(itemEntry["block_state_b64"].toString())
                    }

                    val itemBuilder = ItemData.builder()
                        .definition(SimpleItemDefinition(identifier, ItemPalette.getRuntimeId(identifier), false))
                        .blockDefinition(RuntimeBlockDefinition(blockRuntimeId))
                        .count(1)

                    if (itemEntry.containsKey("damage")) {
                        itemBuilder.damage((itemEntry["damage"] as Double).toInt())
                    }

                    val nbtTag: String? = itemEntry["nbt_b64"] as? String

                    if (nbtTag != null) {
                        ByteArrayInputStream(Base64.getDecoder().decode(nbtTag.toByteArray())).use { byteStream ->
                            NbtUtils.createReaderLE(byteStream).use { stream ->
                                itemBuilder.tag(stream.readTag() as NbtMap)
                            }
                        }
                    }

                    netIdCounter++

                    itemBuilder.netId(netIdCounter)

                    this.creativeItems.add(itemBuilder.build())
                }
            }
        }

        fun getBiomeDefinitions(): NbtMap = this.biomeDefinitions

        fun getEntityIdentifiers(): NbtMap = this.entityIdentifiers

        fun getCreativeItems(): MutableList<ItemData> = this.creativeItems

        private fun readNbt(fileName: String): NbtMap {
            val stream = PaletteUtil::class.java.classLoader.getResourceAsStream(fileName) ?: throw RuntimeException("Could not find $fileName")

            stream.use { inputStream ->
                NbtUtils.createNetworkReader(inputStream).use {
                    return it.readTag() as NbtMap
                }
            }
        }

        private fun runtimeByBlockStateBase64(blockStateBase64: String): Int {
            val data = Base64.getDecoder().decode(blockStateBase64)

            ByteArrayInputStream(data).use {
                NbtUtils.createReaderLE(it).use { stream ->
                    val nbtMap = stream.readTag() as NbtMap
                    for (blockDefinition in BlockPalette.getBlockDefinitions()) {
                        if (blockDefinition.identifier == nbtMap.getString("name") && blockDefinition.state.equals(nbtMap.getCompound("states"))) {
                            return blockDefinition.runtimeId
                        }
                    }
                }
            }
            return 0
        }
    }
}