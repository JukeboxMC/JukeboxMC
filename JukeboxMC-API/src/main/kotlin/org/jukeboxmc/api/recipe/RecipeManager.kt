package org.jukeboxmc.api.recipe

interface RecipeManager {

    fun registerRecipe(recipeId: String, recipe: Recipe)

    fun registerRecipe(recipe: SmeltingRecipe)

    fun getShapelessRecipes(): List<ShapelessRecipe>

    fun getShapedRecipes(): List<ShapedRecipe>

    fun getSmeltingRecipes(): List<SmeltingRecipe>

}