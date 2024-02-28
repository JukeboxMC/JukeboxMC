package org.jukeboxmc.server.recipe

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.ShapelessRecipeData
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.extensions.toJukeboxItem
import java.util.*

class ShapelessRecipe : Recipe() {

    private val ingredients: MutableList<ItemDescriptorWithCount> = mutableListOf()
    private val outputs: MutableList<ItemData> = mutableListOf()

    fun addIngredient(vararg items: Item): ShapelessRecipe {
        val itemDataList: MutableList<ItemDescriptorWithCount> = ArrayList()
        for (item in items) {
            itemDataList.add(ItemDescriptorWithCount.fromItem(item.toJukeboxItem()
                .toItemData().toBuilder().damage(32767).build()))
        }
        ingredients.addAll(itemDataList)
        return this
    }

    fun addOutput(vararg items: Item): ShapelessRecipe {
        val itemDataList: MutableList<ItemData> = ArrayList()
        for (item in items) {
            itemDataList.add(item.toJukeboxItem().toItemData())
        }
        outputs.addAll(itemDataList)
        return this
    }

    override fun doRegister(craftingManager: RecipeManager, recipeId: String): RecipeData {
        return ShapelessRecipeData.shapeless(
            recipeId,
            ingredients,
            outputs,
            UUID.randomUUID(),
            "crafting_table",
            1,
            craftingManager.getHighestNetworkId() + 1
        )
    }
}