package org.jukeboxmc.crafting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.ContainerMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.CraftingDataType;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.PotionMixData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe.*;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ComplexAliasDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.InvalidDescriptor;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemTagDescriptor;
import org.jukeboxmc.Server;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.crafting.recipes.Recipe;
import org.jukeboxmc.crafting.recipes.SmeltingRecipe;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.ItemPalette;
import org.jukeboxmc.util.RuntimeBlockDefination;

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
        InputStream recipesStream = Server.class.getClassLoader().getResourceAsStream( "recipes.json" );
        if ( recipesStream == null ) {
            throw new AssertionError( "Unable to find recipes.json" );
        }
        Config config = new Config( recipesStream, ConfigType.JSON );
        List<Map<String, Object>> recipes = (List<Map<String, Object>>) config.getMap().get( "recipes" );

        for ( Map<String, Object> recipe : recipes ) {
            CraftingDataType craftingDataType = CraftingDataType.valueOf( (String) recipe.get( "type" ) );

            if ( craftingDataType.equals( CraftingDataType.SHAPELESS ) || craftingDataType.equals( CraftingDataType.SHULKER_BOX ) ) {
                String id = (String) recipe.get( "id" );
                UUID uuid = UUID.fromString( (String) recipe.get( "uuid" ) );
                String tag = (String) recipe.get( "tag" );
                int priority = (int) (double) recipe.get( "priority" );
                int netId = (int) (double) recipe.get( "netId" );

                List<ItemDescriptorWithCount> inputItems = new ArrayList<>();
                if ( recipe.containsKey( "inputs" ) ) {
                    List<Map<String, Object>> inputs = (List<Map<String, Object>>) recipe.get( "inputs" );
                    for ( Map<String, Object> input : inputs ) {
                        if ( input.containsKey( "descriptor" ) ) {
                            List<Map<String, Object>> list = (List<Map<String, Object>>) input.get( "descriptor" );
                            for ( Map<String, Object> map : list ) {
                                String type = (String) map.get( "descriptorType" );

                                if ( type.equalsIgnoreCase( "DEFAULT" ) ) {
                                    String identifier = (String) map.get( "identifier" );
                                    int runtimeId = (int) (double) map.get( "runtimeId" );
                                    boolean componentBased = (boolean) map.get( "componentBased" );
                                    int auxValue = (int) (double) map.get( "auxValue" );
                                    inputItems.add( ItemDescriptorWithCount.fromItem( ItemData.builder()
                                            .definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) )
                                            .damage( auxValue )
                                            .count( 1 )
                                            .build() ) );
                                } else if ( type.equalsIgnoreCase( "INVALID" ) ) {
                                    inputItems.add( new ItemDescriptorWithCount( InvalidDescriptor.INSTANCE, 1 ) );
                                } else if ( type.equalsIgnoreCase( "ITEMTAG" ) ) {
                                    String itemTag = (String) map.get( "itemTag" );
                                    inputItems.add( new ItemDescriptorWithCount( new ItemTagDescriptor( itemTag ), 1 ) );
                                } else if ( type.equalsIgnoreCase( "COMPLEX" ) ) {
                                    String name = (String) map.get( "name" );
                                    inputItems.add( new ItemDescriptorWithCount( new ComplexAliasDescriptor( name ), 1 ) );
                                }
                            }
                        }
                    }
                }
                List<ItemData> outputItems = new ArrayList<>();
                if ( recipe.containsKey( "outputs" ) ) {
                    List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get( "outputs" );
                    for ( Map<String, Object> output : outputs ) {
                        int runtimeId = (int) (double) output.get( "id" );
                        String identifier = (String) output.get( "identifier" );
                        boolean componentBased = (boolean) output.get( "componentBased" );
                        int blockRuntimeId = (int) (double) output.getOrDefault( "blockRuntimeId", 0D );
                        int amount = (int) (double) output.get( "amount" );
                        outputItems.add( ItemData.builder()
                                .definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) )
                                .blockDefinition( new RuntimeBlockDefination( blockRuntimeId ) )
                                .count( amount )
                                .build() );
                    }
                }
                this.craftingData.add( ShapelessRecipeData.shapeless( id, inputItems, outputItems, uuid, tag, priority, netId ) );
            } else if ( craftingDataType.equals( CraftingDataType.SHAPED ) ) {
                String id = (String) recipe.get( "id" );
                int width = (int) (double) recipe.get( "width" );
                int height = (int) (double) recipe.get( "height" );
                UUID uuid = UUID.fromString( (String) recipe.get( "uuid" ) );
                String tag = (String) recipe.get( "tag" );
                int priority = (int) (double) recipe.get( "priority" );
                int netId = (int) (double) recipe.get( "netId" );

                List<ItemDescriptorWithCount> inputItems = new ArrayList<>();
                if ( recipe.containsKey( "inputs" ) ) {
                    List<Map<String, Object>> inputs = (List<Map<String, Object>>) recipe.get( "inputs" );
                    for ( Map<String, Object> input : inputs ) {
                        if ( input.containsKey( "descriptor" ) ) {
                            List<Map<String, Object>> list = (List<Map<String, Object>>) input.get( "descriptor" );
                            for ( Map<String, Object> map : list ) {
                                String type = (String) map.get( "descriptorType" );

                                if ( type.equalsIgnoreCase( "DEFAULT" ) ) {
                                    String identifier = (String) map.get( "identifier" );
                                    int runtimeId = (int) (double) map.get( "runtimeId" );
                                    boolean componentBased = (boolean) map.get( "componentBased" );
                                    int auxValue = (int) (double) map.get( "auxValue" );
                                    inputItems.add( ItemDescriptorWithCount.fromItem( ItemData.builder()
                                            .definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) )
                                            .damage( auxValue )
                                            .count( 1 )
                                            .build() ) );
                                } else if ( type.equalsIgnoreCase( "INVALID" ) ) {
                                    inputItems.add( new ItemDescriptorWithCount( InvalidDescriptor.INSTANCE, 1 ) );
                                } else if ( type.equalsIgnoreCase( "ITEMTAG" ) ) {
                                    String itemTag = (String) map.get( "itemTag" );
                                    inputItems.add( new ItemDescriptorWithCount( new ItemTagDescriptor( itemTag ), 1 ) );
                                } else if ( type.equalsIgnoreCase( "COMPLEX" ) ) {
                                    String name = (String) map.get( "name" );
                                    inputItems.add( new ItemDescriptorWithCount( new ComplexAliasDescriptor( name ), 1 ) );
                                }
                            }
                        }
                    }
                }
                List<ItemData> outputItems = new ArrayList<>();
                if ( recipe.containsKey( "outputs" ) ) {
                    List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get( "outputs" );
                    for ( Map<String, Object> output : outputs ) {
                        int runtimeId = (int) (double) output.get( "id" );
                        String identifier = (String) output.get( "identifier" );
                        boolean componentBased = (boolean) output.get( "componentBased" );
                        int blockRuntimeId = (int) (double) output.getOrDefault( "blockRuntimeId", 0D );
                        int amount = (int) (double) output.get( "amount" );
                        outputItems.add( ItemData.builder()
                                .definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) )
                                .blockDefinition( new RuntimeBlockDefination( blockRuntimeId ) )
                                .count( amount )
                                .build() );
                    }
                }
                ShapedRecipeData shaped = ShapedRecipeData.shaped( id, width, height, inputItems, outputItems, uuid, tag, priority, netId );
                this.craftingData.add( shaped );
            } else if ( craftingDataType.equals( CraftingDataType.SMITHING_TRANSFORM ) ) {
                String id = (String) recipe.get( "id" );
                String tag = (String) recipe.get( "tag" );
                int netId = (int) (double) recipe.get( "netId" );
                ItemDescriptorWithCount baseDescriptor = null;
                ItemDescriptorWithCount additionDescriptor = null;
                ItemData resultDescriptor = null;
                if ( recipe.containsKey( "base" ) ) {
                    Map<String, Object> baseMap = (Map<String, Object>) recipe.get( "base" );
                    String identifier = (String) baseMap.get( "identifier" );
                    int runtimeId = (int) (double) baseMap.get( "runtimeId" );
                    boolean componentBased = (boolean) baseMap.get( "componentBased" );
                    int auxValue = (int) (double) baseMap.get( "auxValue" );
                    baseDescriptor = ItemDescriptorWithCount.fromItem( ItemData.builder().definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) ).damage( auxValue ).build() );
                }
                if ( recipe.containsKey( "addition" ) ) {
                    Map<String, Object> additionMap = (Map<String, Object>) recipe.get( "addition" );
                    String identifier = (String) additionMap.get( "identifier" );
                    int runtimeId = (int) (double) additionMap.get( "runtimeId" );
                    boolean componentBased = (boolean) additionMap.get( "componentBased" );
                    int auxValue = (int) (double) additionMap.get( "auxValue" );
                    additionDescriptor = ItemDescriptorWithCount.fromItem( ItemData.builder().definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) ).damage( auxValue ).build() );
                }
                if ( recipe.containsKey( "result" ) ) {
                    Map<String, Object> resultMap = (Map<String, Object>) recipe.get( "result" );
                    String identifier = (String) resultMap.get( "identifier" );
                    int runtimeId = (int) (double) resultMap.get( "runtimeId" );
                    boolean componentBased = (boolean) resultMap.get( "componentBased" );
                    int blockRuntimeId = (int) (double) resultMap.get( "blockRuntimeId" );
                    resultDescriptor = ItemData.builder().definition( new SimpleItemDefinition( identifier, runtimeId, componentBased ) ).blockDefinition( new RuntimeBlockDefination( blockRuntimeId ) ).build();
                }
                this.craftingData.add(SmithingTransformRecipeData.of(id, new ItemDescriptorWithCount(InvalidDescriptor.INSTANCE, 0), baseDescriptor, additionDescriptor, resultDescriptor, tag, netId));
            } else if ( craftingDataType.equals( CraftingDataType.MULTI ) ) {
                String uuid = (String) recipe.get( "uuid" );
                int netId = (int) (double) recipe.get( "netId" );
                this.craftingData.add( MultiRecipeData.of( UUID.fromString( uuid ), netId ) );
            } else if ( craftingDataType.equals( CraftingDataType.FURNACE_DATA ) ) {
                String tag = (String) recipe.get( "tag" );

                int inputId = (int) (double) recipe.get( "inputId" );
                int inputData = (int) (double) recipe.get( "inputData" );

                int id = (int) (double) recipe.get( "id" );
                String identifier = (String) recipe.get( "identifier" );

                boolean componentBased = (boolean) recipe.get( "componentBased" );
                this.craftingData.add( FurnaceRecipeData.of( inputId, inputData, ItemData.builder()
                        .definition( new SimpleItemDefinition( identifier, id, componentBased ) )
                        .build(), tag ) );

                Item input = new Item( ItemPalette.getIdentifier( (short) inputId ), false );
                if ( inputData != 32767 ) {
                    input.setMeta( inputData );
                }
                Item output = new Item( Identifier.fromString( identifier ), false );

                this.smeltingRecipes.add( new SmeltingRecipe( input, output ) );
            }
        }

        List<Map<String, Object>> containerMixes = (List<Map<String, Object>>) config.getMap().get( "containerMixes" );
        for ( Map<String, Object> recipe : containerMixes ) {
            int inputId = (int) (double) recipe.get( "inputId" );
            int reagentId = (int) (double) recipe.get( "reagentId" );
            int outputId = (int) (double) recipe.get( "outputId" );
            this.containerMixData.add( new ContainerMixData( inputId, reagentId, outputId ) );
        }

        List<Map<String, Object>> potionMixes = (List<Map<String, Object>>) config.getMap().get( "potionMixes" );
        for ( Map<String, Object> recipe : potionMixes ) {
            int inputId = (int) (double) recipe.get( "inputId" );
            int inputMeta = (int) (double) recipe.get( "inputMeta" );
            int reagentId = (int) (double) recipe.get( "reagentId" );
            int reagentMeta = (int) (double) recipe.get( "reagentMeta" );
            int outputId = (int) (double) recipe.get( "outputId" );
            int outputMeta = (int) (double) recipe.get( "outputMeta" );
            this.potionMixData.add( new PotionMixData( inputId, inputMeta, reagentId, reagentMeta, outputId, outputMeta ) );
        }
        try {
            recipesStream.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void registerRecipe( String recipeId, Recipe recipe ) {
        try {
            this.craftingData.add( recipe.doRegister( this, recipeId ) );
        } catch ( RuntimeException e ) {
            Server.getInstance().getLogger().error( "Could not register recipe " + recipeId + "!", e );
        }
    }

    public int getHighestNetworkId() {
        return this.craftingData.stream()
                .filter( recipeData -> recipeData instanceof NetworkRecipeData )
                .map( recipeData -> (NetworkRecipeData) recipeData )
                .max( Comparator.comparing( NetworkRecipeData::getNetId ) )
                .map( NetworkRecipeData::getNetId ).orElse( -1 );
    }

    public List<Item> getResultItem( int recipeNetworkId ) {
        List<NetworkRecipeData> collect = this.craftingData.stream()
                .filter( recipeData -> recipeData instanceof NetworkRecipeData )
                .map( recipeData -> (NetworkRecipeData) recipeData ).toList();
        Optional<NetworkRecipeData> optional = collect.stream().filter( networkRecipeData -> networkRecipeData.getNetId() == recipeNetworkId ).findFirst();
        if ( optional.isPresent() ) {
            NetworkRecipeData networkRecipeData = optional.get();
            if ( networkRecipeData instanceof ShapedRecipeData recipeData ) {
                List<Item> items = new LinkedList<>();
                for ( ItemData result : recipeData.getResults() ) {
                    items.add( new Item( result, false ) );
                }
                return items;
            } else if ( networkRecipeData instanceof ShapelessRecipeData recipeData ) {
                List<Item> items = new LinkedList<>();
                for ( ItemData result : recipeData.getResults() ) {
                    items.add( new Item( result, false ) );
                }
                return items;
            }
        }
        return Collections.emptyList();
    }

    public SmeltingRecipe getSmeltingRecipe( Item input ) {
        return this.smeltingRecipes.stream().filter( smeltingRecipe -> smeltingRecipe.getInput().getType().equals( input.getType() ) ).findFirst().orElse( null );
    }
}
