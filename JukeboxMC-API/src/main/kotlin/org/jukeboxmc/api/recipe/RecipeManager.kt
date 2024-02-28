package org.jukeboxmc.api.recipe

interface RecipeManager {

    fun registerRecipe(recipeId: String, recipe: Recipe)

}