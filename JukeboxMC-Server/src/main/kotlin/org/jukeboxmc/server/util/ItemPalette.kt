package org.jukeboxmc.server.util

import com.google.gson.Gson
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.extensions.fromJson

/**
 * @author Kaooot
 * @version 1.0
 */
class ItemPalette {

    companion object {
        private val itemDefinitions = arrayListOf<ItemDefinition>()

        init {
            val stream = ItemPalette::class.java.classLoader.getResourceAsStream("item_palette.json")
                ?: throw RuntimeException("The item palette was not found")
            val gson = Gson()

            stream.reader().use {
                val parseItem = gson.fromJson<List<LinkedHashMap<String, Any>>>(it)

                for (entry in parseItem) {
                    this.itemDefinitions.add(
                        SimpleItemDefinition(
                            entry["name"] as String,
                            (entry["id"] as Double).toInt(),
                            false
                        )
                    )
                }
            }
        }

        fun getItemDefinitions(): List<ItemDefinition> = this.itemDefinitions

        fun getRuntimeId(identifier: String): Int =
            this.itemDefinitions.find { it.identifier == identifier }!!.runtimeId

        fun getIdentifier(runtimeId: Int): Identifier =
            Identifier.fromString(this.itemDefinitions.find { it.runtimeId == runtimeId }!!.identifier)
    }
}