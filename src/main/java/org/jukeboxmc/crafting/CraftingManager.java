package org.jukeboxmc.crafting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.*;
import org.jukeboxmc.Server;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.crafting.recipes.Recipe;
import org.jukeboxmc.crafting.recipes.SmeltingRecipe;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.util.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
public class CraftingManager {

    private final List<RecipeData> craftingData = new ObjectArrayList<>();
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();

    private final Set<SmeltingRecipe> smeltingRecipes = new HashSet<>();
    private final Set<Recipe> recipes = new HashSet<>();

    public CraftingManager() {
        InputStream recipesStream = Server.class.getClassLoader().getResourceAsStream("recipes.json");
        if (recipesStream == null) {
            throw new AssertionError("Unable to find recipes.json");
        }
        Config config = new Config(recipesStream, ConfigType.JSON);
        List<Map<String, Object>> recipes = (List<Map<String, Object>>) config.getMap().get("recipes");

        for (Map<String, Object> recipe : recipes) {
            CraftingDataType craftingDataType = CraftingDataType.values()[(int) (double) recipe.get("type")];

            if (craftingDataType.equals(CraftingDataType.SHAPELESS) || craftingDataType.equals(CraftingDataType.SHULKER_BOX)) {
                String id = (String) recipe.get("id");
                UUID uuid = UUID.fromString((String) recipe.get("uuid"));
                String tag = (String) recipe.get("block");
                int priority = (int) (double) recipe.get("priority");
                int netId = (int) (double) recipe.get("netId");

                List<ItemDescriptorWithCount> inputItems = new ArrayList<>();
                if (recipe.containsKey("input")) {
                    List<Map<String, Object>> inputs = (List<Map<String, Object>>) recipe.get("input");
                    for (Map<String, Object> input : inputs) {
                        String type = (String) input.get("type");

                        if (type.equalsIgnoreCase(ItemDescriptorType.DEFAULT.name().toLowerCase())) {
                            String identifier = (String) input.get("itemId");
                            int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                            int auxValue = input.containsKey("auxValue") ? (int) (double) input.get("auxValue") : 0;
                            inputItems.add(ItemDescriptorWithCount.fromItem(ItemData.builder()
                                    .definition(new SimpleItemDefinition(identifier, runtimeId, false))
                                    .damage(auxValue)
                                    .count(1)
                                    .build()));
                        } else if (type.equalsIgnoreCase(ItemDescriptorType.INVALID.name().toLowerCase())) {
                            inputItems.add(new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 1));
                        } else if (type.equalsIgnoreCase(ItemDescriptorType.ITEM_TAG.name().toLowerCase())) {
                            String itemTag = (String) input.get("itemTag");
                            inputItems.add(new ItemDescriptorWithCount(new ItemTagDescriptor(itemTag), 1));
                        } else if (type.equalsIgnoreCase(ItemDescriptorType.COMPLEX_ALIAS.name().toLowerCase())) {
                            String name = (String) input.get("name");
                            inputItems.add(new ItemDescriptorWithCount(new ComplexAliasDescriptor(name), 1));
                        }
                    }
                }
                List<ItemData> outputItems = new ArrayList<>();
                if (recipe.containsKey("output")) {
                    List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get("output");
                    for (Map<String, Object> output : outputs) {
                        String identifier = (String) output.get("id");
                        int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                        int count = (int) (double) output.get("count");
                        outputItems.add(ItemData.builder()
                                .definition(new SimpleItemDefinition(identifier, runtimeId, false))
                                .blockDefinition(new RuntimeBlockDefination(CreativeItems.IDENTIFIER_TO_BLOCK_RUNTIME_ID.getOrDefault(Identifier.fromString(identifier), 0)))
                                .count(count)
                                .build());
                    }
                }
                this.craftingData.add(ShapelessRecipeData.shapeless(id, inputItems, outputItems, uuid, tag, priority, netId));
            } else if (craftingDataType.equals(CraftingDataType.SHAPED)) {
                String id = (String) recipe.get("id");
                int width = (int) (double) recipe.get("width");
                int height = (int) (double) recipe.get("height");
                UUID uuid = UUID.fromString((String) recipe.get("uuid"));
                String block = (String) recipe.get("block");
                int priority = (int) (double) recipe.get("priority");
                int netId = (int) (double) recipe.get("netId");

                List<ItemDescriptorWithCount> inputItems = new ArrayList<>();
                if (recipe.containsKey("input")) {
                    Map<String, Object> inputs = (Map<String, Object>) recipe.get("input");
                    for (Map.Entry<String, Object> entry : inputs.entrySet()) {
                        if (entry.getValue() instanceof Map<?, ?>) {
                            final Map<String, Object> input = (Map<String, Object>) entry.getValue();

                            if (input.containsKey("type")) {
                                String type = (String) input.get("type");

                                if (type.equalsIgnoreCase(ItemDescriptorType.DEFAULT.name().toLowerCase())) {
                                    String identifier = (String) input.get("itemId");
                                    int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                                    int auxValue = input.containsKey("auxValue") ? (int) (double) input.get("auxValue") : 0;
                                    inputItems.add(ItemDescriptorWithCount.fromItem(ItemData.builder()
                                            .definition(new SimpleItemDefinition(identifier, runtimeId, false))
                                            .damage(auxValue)
                                            .count(1)
                                            .build()));
                                } else if (type.equalsIgnoreCase(ItemDescriptorType.INVALID.name().toLowerCase())) {
                                    inputItems.add(new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 1));
                                } else if (type.equalsIgnoreCase(ItemDescriptorType.ITEM_TAG.name().toLowerCase())) {
                                    String itemTag = (String) input.get("itemTag");
                                    inputItems.add(new ItemDescriptorWithCount(new ItemTagDescriptor(itemTag), 1));
                                } else if (type.equalsIgnoreCase(ItemDescriptorType.COMPLEX_ALIAS.name().toLowerCase())) {
                                    String name = (String) input.get("name");
                                    inputItems.add(new ItemDescriptorWithCount(new ComplexAliasDescriptor(name), 1));
                                }
                            }
                        }
                    }
                }
                List<ItemData> outputItems = new ArrayList<>();
                if (recipe.containsKey("output")) {
                    List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get("output");
                    for (Map<String, Object> output : outputs) {
                        String identifier = (String) output.get("id");
                        int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                        int count = (int) (double) output.get("count");
                        outputItems.add(ItemData.builder()
                                .definition(new SimpleItemDefinition(identifier, runtimeId, false))
                                .blockDefinition(new RuntimeBlockDefination(CreativeItems.IDENTIFIER_TO_BLOCK_RUNTIME_ID.getOrDefault(Identifier.fromString(identifier), 0)))
                                .count(count)
                                .build());
                    }
                }
                ShapedRecipeData shaped = ShapedRecipeData.shaped(id, width, height, inputItems, outputItems, uuid, block, priority, netId);
                this.craftingData.add(shaped);
            } else if (craftingDataType.equals(CraftingDataType.SMITHING_TRANSFORM)) {
                String id = (String) recipe.get("id");
                String block = (String) recipe.get("block");
                int netId = (int) (double) recipe.get("netId");
                ItemDescriptorWithCount baseDescriptor = null;
                ItemDescriptorWithCount additionDescriptor = null;
                ItemData resultDescriptor = null;
                if (recipe.containsKey("base")) {
                    Map<String, Object> baseMap = (Map<String, Object>) recipe.get("base");
                    String identifier = (String) baseMap.get("itemId");
                    int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                    int auxValue = (int) (double) baseMap.get("auxValue");
                    baseDescriptor = ItemDescriptorWithCount.fromItem(ItemData.builder().definition(new SimpleItemDefinition(identifier, runtimeId, false)).damage(auxValue).build());
                }
                if (recipe.containsKey("addition")) {
                    Map<String, Object> additionMap = (Map<String, Object>) recipe.get("addition");
                    String identifier = (String) additionMap.get("itemId");
                    int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                    int auxValue = (int) (double) additionMap.get("auxValue");
                    additionDescriptor = ItemDescriptorWithCount.fromItem(ItemData.builder().definition(new SimpleItemDefinition(identifier, runtimeId, false)).damage(auxValue).build());
                }
                if (recipe.containsKey("template")) {
                    Map<String, Object> templateMap = (Map<String, Object>) recipe.get("template");
                    String identifier = (String) templateMap.get("itemId");
                    int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                    int auxValue = (int) (double) templateMap.get("auxValue");
                    additionDescriptor = ItemDescriptorWithCount.fromItem(ItemData.builder().definition(new SimpleItemDefinition(identifier, runtimeId, false)).damage(auxValue).build());
                }
                if (recipe.containsKey("result")) {
                    Map<String, Object> resultMap = (Map<String, Object>) recipe.get("result");
                    String identifier = (String) resultMap.get("id");
                    int runtimeId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(identifier));
                    resultDescriptor = ItemData.builder().definition(new SimpleItemDefinition(identifier, runtimeId, false)).blockDefinition(new RuntimeBlockDefination(CreativeItems.IDENTIFIER_TO_BLOCK_RUNTIME_ID.getOrDefault(Identifier.fromString(identifier), 0))).build();
                }
                this.craftingData.add(SmithingTransformRecipeData.of(id, new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 0), baseDescriptor, additionDescriptor, resultDescriptor, block, netId));
            } else if (craftingDataType.equals(CraftingDataType.SMITHING_TRIM)) {
                ItemDescriptorWithCount baseDescriptor = null;
                ItemDescriptorWithCount additionDescriptor = null;
                ItemDescriptorWithCount templateDescriptor = null;

                if (recipe.containsKey("base")) {
                    final Map<String, Object> baseMap = ((Map<String, Object>) recipe.get("base"));

                    baseDescriptor = new ItemDescriptorWithCount(new ItemTagDescriptor((String) baseMap.get("itemTag")), (int) (double) baseMap.get("count"));
                }

                if (recipe.containsKey("addition")) {
                    final Map<String, Object> baseMap = ((Map<String, Object>) recipe.get("addition"));

                    additionDescriptor = new ItemDescriptorWithCount(new ItemTagDescriptor((String) baseMap.get("itemTag")), (int) (double) baseMap.get("count"));
                }

                if (recipe.containsKey("template")) {
                    final Map<String, Object> baseMap = ((Map<String, Object>) recipe.get("template"));

                    templateDescriptor = new ItemDescriptorWithCount(new ItemTagDescriptor((String) baseMap.get("itemTag")), (int) (double) baseMap.get("count"));
                }

                this.craftingData.add(SmithingTrimRecipeData.of((String) recipe.get("id"), baseDescriptor, additionDescriptor, templateDescriptor, ((String) recipe.get("block")), ((int) (double) recipe.get("netId"))));
            } else if (craftingDataType.equals(CraftingDataType.MULTI)) {
                String uuid = (String) recipe.get("uuid");
                int netId = (int) (double) recipe.get("netId");
                this.craftingData.add(MultiRecipeData.of(UUID.fromString(uuid), netId));
            } else if (craftingDataType.equals(CraftingDataType.FURNACE_DATA)) {
                String block = (String) recipe.get("block");
                Map<String, Object> inputMap = (Map<String, Object>) recipe.get("input");

                int inputId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(inputMap.get("id").toString()));
                int inputData = 0;

                if (inputMap.containsKey("damage")) {
                    inputData = (int) (double) inputMap.get("damage");
                }

                Map<String, Object> outputMap = (Map<String, Object>) recipe.get("output");
                String outputId = outputMap.get("id").toString();

                this.craftingData.add(FurnaceRecipeData.of(inputId, inputData, ItemData.builder()
                        .definition(new SimpleItemDefinition(outputId, (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(outputId)), false))
                        .build(), block));

                Item input = new Item(ItemPalette.getIdentifier((short) inputId), false);
                if (inputData != 32767) {
                    input.setMeta(inputData);
                }
                Item output = new Item(Identifier.fromString(outputId), false);

                this.smeltingRecipes.add(new SmeltingRecipe(input, output));
            }
        }

        List<Map<String, Object>> containerMixes = (List<Map<String, Object>>) config.getMap().get("containerMixes");
        for (Map<String, Object> recipe : containerMixes) {
            int inputId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("inputId").toString()));
            int reagentId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("reagentId").toString()));
            int outputId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("outputId").toString()));
            this.containerMixData.add(new ContainerMixData(inputId, reagentId, outputId));
        }

        List<Map<String, Object>> potionMixes = (List<Map<String, Object>>) config.getMap().get("potionMixes");
        for (Map<String, Object> recipe : potionMixes) {
            int inputId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("inputId").toString()));
            int inputMeta = (int) (double) recipe.get("inputMeta");
            int reagentId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("reagentId").toString()));
            int reagentMeta = (int) (double) recipe.get("reagentMeta");
            int outputId = (int) ItemPalette.IDENTIFIER_TO_RUNTIME.get(Identifier.fromString(recipe.get("outputId").toString()));
            int outputMeta = (int) (double) recipe.get("outputMeta");
            this.potionMixData.add(new PotionMixData(inputId, inputMeta, reagentId, reagentMeta, outputId, outputMeta));
        }
        try {
            recipesStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerRecipe(String recipeId, Recipe recipe) {
        try {
            this.craftingData.add(recipe.doRegister(this, recipeId));
        } catch (RuntimeException e) {
            Server.getInstance().getLogger().error("Could not register recipe " + recipeId + "!", e);
        }
    }

    public int getHighestNetworkId() {
        return this.craftingData.stream()
                .filter(recipeData -> recipeData instanceof NetworkRecipeData)
                .map(recipeData -> (NetworkRecipeData) recipeData)
                .max(Comparator.comparing(NetworkRecipeData::getNetId))
                .map(NetworkRecipeData::getNetId).orElse(-1);
    }

    public List<Item> getResultItem(int recipeNetworkId) {
        List<NetworkRecipeData> collect = this.craftingData.stream()
                .filter(recipeData -> recipeData instanceof NetworkRecipeData)
                .map(recipeData -> (NetworkRecipeData) recipeData).toList();
        Optional<NetworkRecipeData> optional = collect.stream().filter(networkRecipeData -> networkRecipeData.getNetId() == recipeNetworkId).findFirst();
        if (optional.isPresent()) {
            NetworkRecipeData networkRecipeData = optional.get();
            if (networkRecipeData instanceof ShapedRecipeData recipeData) {
                List<Item> items = new LinkedList<>();
                for (ItemData result : recipeData.getResults()) {
                    items.add(new Item(result, false));
                }
                return items;
            } else if (networkRecipeData instanceof ShapelessRecipeData recipeData) {
                List<Item> items = new LinkedList<>();
                for (ItemData result : recipeData.getResults()) {
                    items.add(new Item(result, false));
                }
                return items;
            }
        }
        return Collections.emptyList();
    }

    public SmeltingRecipe getSmeltingRecipe(Item input) {
        return this.smeltingRecipes.stream().filter(smeltingRecipe -> smeltingRecipe.getInput().getType().equals(input.getType())).findFirst().orElse(null);
    }
}
