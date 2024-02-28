package org.jukeboxmc.server.recipe

import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.RecipeData

abstract class Recipe {

    abstract fun doRegister(craftingManager: RecipeManager, recipeId: String): RecipeData

}