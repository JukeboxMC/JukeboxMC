package org.jukeboxmc.server.util

import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.block.BlockRegistry
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import java.io.FileOutputStream

/**
 * @author Kaooot
 * @version 1.0
 */
class RegistryDumper {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val itemRegistry = ItemRegistry()
            val itemField = ItemRegistry::class.java.getDeclaredField("itemClassFromItemType")
            itemField.isAccessible = true
            val items = itemField.get(itemRegistry) as Map<ItemType, Class<out JukeboxItem?>>
            FileOutputStream("item_registry.csv").use {
                val stringBuilder = StringBuilder()
                for (mutableEntry in items) {
                    stringBuilder.append("minecraft:" + mutableEntry.key.name.lowercase()).append(",")
                        .append(mutableEntry.value.simpleName).append("\n")
                }
                it.write(stringBuilder.toString().toByteArray())
            }

            val blockRegistry = BlockRegistry()
            val blockField = BlockRegistry::class.java.getDeclaredField("blockClassFromBlockType")
            blockField.isAccessible = true
            val blocks = blockField.get(blockRegistry) as Map<BlockType, Class<out JukeboxBlock?>>
            FileOutputStream("block_registry.csv").use {
                val stringBuilder = StringBuilder()
                for (mutableEntry in blocks) {
                    stringBuilder.append("minecraft:" + mutableEntry.key.name.lowercase()).append(",")
                        .append(mutableEntry.value.simpleName).append("\n")
                }
                it.write(stringBuilder.toString().toByteArray())
            }
        }
    }
}