package org.jukeboxmc.api.recipe

import org.jukeboxmc.api.item.Item

class SmeltingRecipe(
    private val input: Item,
    private val output: Item,
    private val type: Type
) : Recipe {

    fun getInput(): Item {
        return this.input
    }

    fun getOutput(): Item {
        return this.output
    }

    fun getType(): Type {
        return this.type
    }

    enum class Type {
        FURNACE,
        BLAST_FURNACE,
        SMOKER,
        SOUL_CAMPFIRE,
        CAMPFIRE,
    }

}