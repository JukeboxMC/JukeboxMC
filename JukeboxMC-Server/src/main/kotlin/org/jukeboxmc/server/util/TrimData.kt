package org.jukeboxmc.server.util

import com.google.gson.Gson
import org.cloudburstmc.protocol.bedrock.data.TrimMaterial
import org.cloudburstmc.protocol.bedrock.data.TrimPattern
import org.jukeboxmc.api.extensions.fromJson

class TrimData {

    companion object {

        private val pattern = arrayListOf<TrimPattern>()
        private val material = arrayListOf<TrimMaterial>()

        init {
            val stream = ItemPalette::class.java.classLoader.getResourceAsStream("trim_data.json") ?: throw RuntimeException("The item palette was not found")
            val gson = Gson()

            stream.reader().use {
                val parseItem = gson.fromJson<Map<String, List<Map<String, String>>>>(it)

                for (patternValue in parseItem["patterns"]!!) {
                   this.pattern.add(TrimPattern(patternValue["itemName"], patternValue["patternId"]))
                }

                for (materialValue in parseItem["materials"]!!) {
                    this.material.add(TrimMaterial(materialValue["materialId"], materialValue["color"], materialValue["itemName"]))
                }
            }
        }

        fun getPattern(): List<TrimPattern> = this.pattern

        fun getMaterial(): List<TrimMaterial> = this.material
    }

}