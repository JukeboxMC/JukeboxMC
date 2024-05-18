package org.jukeboxmc.server.recipe

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ComplexAliasDescriptor
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.DefaultDescriptor
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemTagDescriptor
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.extensions.fromJson
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.recipe.*
import org.jukeboxmc.server.block.RuntimeBlockDefinition
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.ItemPalette
import org.jukeboxmc.server.util.PaletteUtil
import java.util.*


class JukeboxRecipeManager : RecipeManager {

    private val craftingData: MutableList<RecipeData> = mutableListOf()
    private val containerMixData: MutableList<ContainerMixData> = mutableListOf()
    private val potionMixData: MutableList<PotionMixData> = mutableListOf()

    private val shapelessRecipes: MutableList<ShapelessRecipe> = mutableListOf()
    private val shapedRecipes: MutableList<ShapedRecipe> = mutableListOf()
    private val smeltingRecipes: MutableList<SmeltingRecipe> = mutableListOf()

    init {
        val stream = ItemPalette::class.java.classLoader.getResourceAsStream("recipes.json")
            ?: throw RuntimeException("The item palette was not found")
        val gson = Gson()

        stream.reader().use { inputStreamReader ->
            val fromJosn = gson.fromJson<JsonObject>(inputStreamReader)
            val recipes = fromJosn.getAsJsonArray("recipes")
            for (element in recipes) {
                val jsonObject = element.asJsonObject
                val type = CraftingDataType.entries[jsonObject["type"].asInt]

                if (type == CraftingDataType.SHAPELESS && type == CraftingDataType.SHULKER_BOX) {
                    val id = jsonObject["id"].asString
                    val block = jsonObject["block"].asString
                    val uuid = UUID.fromString(jsonObject["uuid"].asString)
                    val netId = jsonObject["netId"].asInt
                    val priority = jsonObject["priority"].asInt

                    val inputItems: MutableList<ItemDescriptorWithCount> = mutableListOf()
                    for (inputElement in jsonObject["input"].asJsonArray) {
                        val inputObject = inputElement.asJsonObject

                        val count = inputObject["count"].asInt
                        val identifier = inputObject["itemId"].asString
                        val auxValue = inputObject["auxValue"].asInt
                        val runtimeId = ItemPalette.getRuntimeId(identifier)
                        inputItems.add(
                            ItemDescriptorWithCount.fromItem(
                                ItemData.builder()
                                    .definition(SimpleItemDefinition(identifier, runtimeId, false))
                                    .damage(auxValue)
                                    .count(count)
                                    .build()
                            )
                        )
                    }
                    val outputItems: MutableList<ItemData> = mutableListOf()
                    for (outputElement in jsonObject["output"].asJsonArray) {
                        val outputObject = outputElement.asJsonObject
                        val identifier = outputObject["id"].asString
                        val count = outputObject["count"].asInt
                        val runtimeId = ItemPalette.getRuntimeId(identifier)
                        outputItems.add(
                            ItemData.builder()
                                .definition(SimpleItemDefinition(identifier, runtimeId, false))
                                .blockDefinition(RuntimeBlockDefinition(PaletteUtil.identifierToBlockRuntimeId[identifier]!!))
                                .count(count)
                                .build()
                        )
                    }

                    this.craftingData.add(
                        ShapelessRecipeData.shapeless(
                            id,
                            inputItems,
                            outputItems,
                            uuid,
                            block,
                            priority,
                            netId
                        )
                    )

                    this.shapelessRecipes.add(ShapelessRecipe().apply {
                        this.getIngredients().addAll(inputItems.map { JukeboxItem(it.toItem(), false) }.toList())
                        this.getOutputs().addAll(outputItems.map { JukeboxItem(it, false) }.toList())
                    })

                } else if (type == CraftingDataType.SHAPED) {
                    val identifier = jsonObject["id"].asString
                    val block = jsonObject["block"].asString
                    val uuid = UUID.fromString(jsonObject["uuid"].asString)
                    val netId = jsonObject["netId"].asInt
                    val priority = jsonObject["priority"].asInt
                    val width = jsonObject["width"].asInt
                    val height = jsonObject["height"].asInt

                    val inputItems: MutableList<ItemDescriptorWithCount> = mutableListOf()
                    val charMap: MutableMap<Char, ItemDescriptorWithCount> = mutableMapOf()


                    for (entry in jsonObject["input"].asJsonObject.entrySet()) {
                        val entryJsonObject = entry.value.asJsonObject
                        val itemType = entryJsonObject["type"].asString

                        if (itemType.equals("default")) {
                            if (!charMap.containsKey(entry.key[0])) {
                                val id = entryJsonObject["itemId"].asString
                                val runtimeId = ItemPalette.getRuntimeId(id)
                                charMap[entry.key[0]] = ItemDescriptorWithCount.fromItem(
                                    ItemData.builder()
                                        .definition(SimpleItemDefinition(id, runtimeId, false))
                                        .damage(entryJsonObject["auxValue"].asInt)
                                        .count(entryJsonObject["count"].asInt)
                                        .build()
                                )
                            }
                        } else if (itemType.equals("item_tag")) {
                            if (!charMap.containsKey(entry.key[0])) {
                                charMap[entry.key[0]] = ItemDescriptorWithCount(
                                    ItemTagDescriptor(entryJsonObject["itemTag"].asString),
                                    1
                                )
                            }
                        } else if (itemType.equals("complex_alias")) {
                            if (!charMap.containsKey(entry.key[0])) {
                                charMap[entry.key[0]] = ItemDescriptorWithCount(
                                    ComplexAliasDescriptor(entryJsonObject["name"].asString),
                                    1
                                )
                            }
                        }
                    }

                    for (y in 0 until height) {
                        for (x in 0 until width) {
                            val value = jsonObject["shape"].asJsonArray[y].asString[x]
                            if (charMap.containsKey(value)) {
                                inputItems.add(charMap[value]!!)
                            } else {
                                inputItems.add(ItemDescriptorWithCount.EMPTY)
                            }
                        }
                    }

                    val outputItems: MutableList<ItemData> = mutableListOf()
                    for (outputElement in jsonObject["output"].asJsonArray) {
                        val outputObject = outputElement.asJsonObject
                        val id = outputObject["id"].asString
                        val count = outputObject["count"].asInt
                        val runtimeId = ItemPalette.getRuntimeId(id)
                        outputItems.add(
                            ItemData.builder()
                                .definition(SimpleItemDefinition(id, runtimeId, false))
                                .blockDefinition(RuntimeBlockDefinition(PaletteUtil.identifierToBlockRuntimeId[id]!!))
                                .count(count)
                                .build()
                        )
                    }
                    this.craftingData.add(ShapedRecipeData.shaped(
                        identifier,
                        width,
                        height,
                        inputItems,
                        outputItems,
                        uuid,
                        block,
                        priority,
                        netId,
                        false
                    ))

                    val shaped: MutableList<String> = mutableListOf()
                    for (shapeElement in jsonObject["shape"].asJsonArray) {
                        shaped.add(shapeElement.asString)
                    }

                    this.shapedRecipes.add(ShapedRecipe().apply {
                        this.getIngredients().putAll(charMap.mapValues { (_, value) ->
                            if (value.descriptor is DefaultDescriptor) {
                                val itemData = value.toItem()
                                if (ItemRegistry.getItemTypeFromIdentifier().containsKey(Identifier.fromString(itemData.definition.identifier))) {
                                    JukeboxItem(itemData, false)
                                } else {
                                    JukeboxItem(ItemType.AIR, false)
                                }
                            } else {
                                JukeboxItem(ItemType.AIR, false)
                            }
                        }.toMutableMap())

                        this.getOutputs().addAll(outputItems.map { JukeboxItem(it, false) }.toList())
                        this.shape(*shaped.toTypedArray())
                    })
                } else if (type == CraftingDataType.SMITHING_TRANSFORM) {
                    val id = jsonObject["id"].asString
                    val block = jsonObject["block"].asString
                    val netId = jsonObject["netId"].asInt

                    val baseObject = jsonObject["base"].asJsonObject
                    val baseItemId = baseObject["itemId"].asString
                    val baseRuntimeId = ItemPalette.getRuntimeId(baseItemId)
                    val baseDescriptor: ItemDescriptorWithCount = ItemDescriptorWithCount.fromItem(
                        ItemData.builder()
                            .definition(SimpleItemDefinition(baseItemId, baseRuntimeId, false))
                            .damage(baseObject["auxValue"].asInt)
                            .count(baseObject["count"].asInt)
                            .build()
                    )

                    val additionObject = jsonObject["addition"].asJsonObject
                    val additionItemId = additionObject["itemId"].asString
                    val additionRuntimeId = ItemPalette.getRuntimeId(additionItemId)
                    val additionDescriptor: ItemDescriptorWithCount = ItemDescriptorWithCount.fromItem(
                        ItemData.builder()
                            .definition(SimpleItemDefinition(additionItemId, additionRuntimeId, false))
                            .damage(additionObject["auxValue"].asInt)
                            .count(additionObject["count"].asInt)
                            .build()
                    )

                    val templateObject = jsonObject["template"].asJsonObject
                    val templateItemId = templateObject["itemId"].asString
                    val templateRuntimeId = ItemPalette.getRuntimeId(templateItemId)
                    val templateDescriptor: ItemDescriptorWithCount = ItemDescriptorWithCount.fromItem(
                        ItemData.builder()
                            .definition(SimpleItemDefinition(templateItemId, templateRuntimeId, false))
                            .damage(templateObject["auxValue"].asInt)
                            .count(templateObject["count"].asInt)
                            .build()
                    )

                    val resultObject = jsonObject["result"].asJsonObject
                    val resultId = resultObject["id"].asString
                    val resultRuntimeId = ItemPalette.getRuntimeId(resultId)
                    val resultDescriptor: ItemData = ItemData.builder()
                        .definition(SimpleItemDefinition(resultId, resultRuntimeId, false))
                        .damage(0)
                        .count(resultObject["count"].asInt)
                        .build()

                    this.craftingData.add(
                        SmithingTransformRecipeData.of(
                            id,
                            templateDescriptor,
                            baseDescriptor,
                            additionDescriptor,
                            resultDescriptor,
                            block,
                            netId
                        )
                    )
                } else if (type == CraftingDataType.SMITHING_TRIM) {
                    val id = jsonObject["id"].asString
                    val block = jsonObject["block"].asString
                    val netId = jsonObject["netId"].asInt

                    val baseObject = jsonObject["base"].asJsonObject
                    val baseDescriptor = ItemDescriptorWithCount(
                        ItemTagDescriptor(baseObject["itemTag"].asString),
                        baseObject["count"].asInt
                    )

                    val additionObject = jsonObject["addition"].asJsonObject
                    val additionDescriptor = ItemDescriptorWithCount(
                        ItemTagDescriptor(additionObject["itemTag"].asString),
                        additionObject["count"].asInt
                    )

                    val templateObject = jsonObject["template"].asJsonObject
                    val templateDescriptor = ItemDescriptorWithCount(
                        ItemTagDescriptor(templateObject["itemTag"].asString),
                        templateObject["count"].asInt
                    )

                    this.craftingData.add(
                        SmithingTrimRecipeData.of(
                            id,
                            baseDescriptor,
                            additionDescriptor,
                            templateDescriptor,
                            block,
                            netId
                        )
                    )
                } else if (type == CraftingDataType.MULTI) {
                    this.craftingData.add(
                        MultiRecipeData.of(
                            UUID.fromString(jsonObject["uuid"].asString),
                            jsonObject["netId"].asInt
                        )
                    )
                } else if (type == CraftingDataType.FURNACE_DATA) {
                    val block = jsonObject["block"].asString
                    val inputObject = jsonObject["input"].asJsonObject
                    val outputObject = jsonObject["output"].asJsonObject

                    val inputItemId = ItemPalette.getRuntimeId(inputObject["id"].asString)
                    val outputItemData = ItemData.builder()
                        .definition(
                            SimpleItemDefinition(
                                outputObject["id"].asString,
                                ItemPalette.getRuntimeId(outputObject["id"].asString),
                                false
                            )
                        )
                        .damage(0)
                        .count(outputObject["count"].asInt)
                        .build()

                    this.craftingData.add(
                        FurnaceRecipeData.of(
                            inputItemId,
                            outputItemData,
                            block
                        )
                    )
                    val identifier = ItemPalette.getIdentifier(inputItemId)
                    val itemType = ItemRegistry.getItemType(identifier)
                    val inputItem = JukeboxItem(itemType, false)
                    val outputItem = JukeboxItem(outputItemData, false)
                    this.smeltingRecipes.add(
                        SmeltingRecipe(
                            inputItem,
                            outputItem,
                            SmeltingRecipe.Type.valueOf(block.uppercase())
                        )
                    )
                }
            }

            val containerMixes = fromJosn.getAsJsonArray("containerMixes")
            for (element in containerMixes) {
                val jsonObject = element.asJsonObject
                val inputId = ItemPalette.getRuntimeId(jsonObject["inputId"].asString)
                val reagentId = ItemPalette.getRuntimeId(jsonObject["reagentId"].asString)
                val outputId = ItemPalette.getRuntimeId(jsonObject["outputId"].asString)
                this.containerMixData.add(ContainerMixData(inputId, reagentId, outputId))
            }

            val potionMixes = fromJosn.getAsJsonArray("potionMixes")
            for (element in potionMixes) {
                val jsonObject = element.asJsonObject
                val inputId = ItemPalette.getRuntimeId(jsonObject["inputId"].asString)
                val inputMeta = jsonObject["inputMeta"].asInt
                val reagentId = ItemPalette.getRuntimeId(jsonObject["reagentId"].asString)
                val reagentMeta = jsonObject["reagentMeta"].asInt
                val outputId = ItemPalette.getRuntimeId(jsonObject["outputId"].asString)
                val outputMeta = jsonObject["outputMeta"].asInt
                this.potionMixData.add(PotionMixData(inputId, inputMeta, reagentId, reagentMeta, outputId, outputMeta))
            }
        }
    }

    fun getResultItem(recipeNetworkId: Int): List<JukeboxItem> {
        val collect = this.craftingData.stream()
            .filter { recipeData: RecipeData? -> recipeData is NetworkRecipeData }
            .map { recipeData: RecipeData -> recipeData as NetworkRecipeData }.toList()
        val optional = collect.stream()
            .filter { networkRecipeData: NetworkRecipeData -> networkRecipeData.netId == recipeNetworkId }
            .findFirst()
        if (optional.isPresent) {
            val networkRecipeData = optional.get()
            if (networkRecipeData is ShapedRecipeData) {
                val items: MutableList<JukeboxItem> = LinkedList()
                for (result in networkRecipeData.results) {
                    items.add(JukeboxItem(result, false))
                }
                return items
            } else if (networkRecipeData is ShapelessRecipeData) {
                val items: MutableList<JukeboxItem> = LinkedList()
                for (result in networkRecipeData.results) {
                    items.add(JukeboxItem(result, false))
                }
                return items
            }
        }
        return emptyList()
    }

    fun getHighestNetworkId(): Int {
        return this.craftingData.stream()
            .filter { recipeData: RecipeData? -> recipeData is NetworkRecipeData }
            .map { recipeData: RecipeData -> recipeData as NetworkRecipeData }
            .max(Comparator.comparing { obj: NetworkRecipeData -> obj.netId })
            .map { obj: NetworkRecipeData -> obj.netId }.orElse(-1)
    }

    fun getCraftingData(): MutableList<RecipeData> = this.craftingData

    fun getContainerMixData(): MutableList<ContainerMixData> = this.containerMixData

    fun getPotionMixData(): MutableList<PotionMixData> = this.potionMixData

    override fun registerRecipe(recipeId: String, recipe: Recipe) {
        if (recipe is ShapelessRecipe) {
            this.craftingData.add(recipe.doRegister(this, recipeId))
        } else if (recipe is ShapedRecipe) {
            this.craftingData.add(recipe.doRegister(this, recipeId))
        }
    }

    override fun registerRecipe(recipe: SmeltingRecipe) {
        this.craftingData.add(recipe.doRegister())
    }

    override fun getShapelessRecipes(): List<ShapelessRecipe> {
        return this.shapelessRecipes.toList()
    }

    override fun getShapedRecipes(): List<ShapedRecipe> {
        return this.shapedRecipes.toList()
    }

    override fun getSmeltingRecipes(): List<SmeltingRecipe> {
        return this.smeltingRecipes.toList()
    }

    fun ShapelessRecipe.doRegister(recipeManager: JukeboxRecipeManager, recipeId: String): RecipeData {
        return ShapelessRecipeData.shapeless(
            recipeId,
            this.getIngredients().map { ItemDescriptorWithCount.fromItem(it.toJukeboxItem().toItemData()) }
                .toMutableList(),
            this.getOutputs().map { it.toJukeboxItem().toItemData() }.toMutableList(),
            UUID.randomUUID(),
            "crafting_table",
            1,
            recipeManager.getHighestNetworkId() + 1
        )
    }

    fun ShapedRecipe.doRegister(recipeManager: JukeboxRecipeManager, recipeId: String): RecipeData {
        val ingredients: MutableList<ItemDescriptorWithCount> = mutableListOf()
        for (s in this.getPattern()!!) {
            val chars = s.toCharArray()
            for (c in chars) {
                if (c == ' ') {
                    ingredients.add(ItemDescriptorWithCount.EMPTY)
                    continue
                }
                ingredients.add(
                    ItemDescriptorWithCount.fromItem(
                        this.getIngredients()[c]!!.toJukeboxItem().toItemData()
                    )
                )
            }
        }
        return ShapedRecipeData.shaped(
            recipeId,
            this.getPattern()!![0].length,
            this.getPattern()!!.size,
            ingredients,
            this.getOutputs().map { it.toJukeboxItem().toItemData() }.toMutableList(),
            UUID.randomUUID(),
            "crafting_table",
            1,
            recipeManager.getHighestNetworkId() + 1,
            false
        )
    }

    fun SmeltingRecipe.doRegister(): RecipeData {
        return FurnaceRecipeData.of(
            this.getInput().getNetworkId(),
            this.getOutput().toJukeboxItem().toItemData(),
            this.getType().name.lowercase()
        )
    }
}