package org.jukeboxmc.api.recipe

import org.jukeboxmc.api.item.Item

open class ShapedRecipe : Recipe {

    private val ingredients: MutableMap<Char, Item> = mutableMapOf()
    private val outputs: MutableList<Item> = mutableListOf()
    private var pattern: Array<String>? = null

    fun shape(vararg pattern: String): ShapedRecipe {
        if (pattern.size > 3 || pattern.isEmpty()) {
            throw RuntimeException("Shape height must be between 3 and 1!")
        }
        val first = pattern[0]
        if (first.length > 3 || first.isEmpty()) {
            throw RuntimeException("Shape width must be between 3 and 1!")
        }
        val last = first.length
        for (line in pattern) {
            if (line.length != last) {
                throw RuntimeException("Shape columns must have the same length!")
            }
        }
        this.pattern = pattern.asList().toTypedArray()
        return this
    }

    fun setIngredient(symbol: Char, item: Item): ShapedRecipe {
        this.ingredients[symbol] = item
        return this
    }

    fun addOutput(vararg items: Item): ShapedRecipe {
        val itemDataList: MutableList<Item> = mutableListOf()
        for (item in items) {
            itemDataList.add(item)
        }
        this.outputs.addAll(itemDataList)
        return this
    }

    fun getIngredients(): MutableMap<Char, Item> {
        return this.ingredients
    }

    fun getOutputs(): MutableList<Item> {
        return this.outputs
    }

    fun getPattern(): Array<String>? {
        return this.pattern
    }

}