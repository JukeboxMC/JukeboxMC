package org.jukeboxmc.api.recipe

import org.jukeboxmc.api.item.Item

open class ShapelessRecipe : Recipe {

    private val ingredients: MutableList<Item> = mutableListOf()
    private val outputs: MutableList<Item> = mutableListOf()

    fun addIngredient(vararg items: Item): ShapelessRecipe {
        val itemDataList: MutableList<Item> = mutableListOf()
        for (item in items) {
            itemDataList.add(item)
        }
        this.ingredients.addAll(itemDataList)
        return this
    }

    fun addOutput(vararg items: Item): ShapelessRecipe {
        val itemDataList: MutableList<Item> = mutableListOf()
        for (item in items) {
            itemDataList.add(item)
        }
        this.outputs.addAll(itemDataList)
        return this
    }

    fun getIngredients(): MutableList<Item> {
        return this.ingredients
    }

    fun getOutputs(): MutableList<Item> {
        return this.outputs
    }

    override fun toString(): String {
        return "ShapelessRecipe(ingredients=$ingredients, outputs=$outputs)"
    }

}