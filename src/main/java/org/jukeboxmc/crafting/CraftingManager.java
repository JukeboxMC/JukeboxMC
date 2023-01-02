package org.jukeboxmc.crafting;

import com.nukkitx.protocol.bedrock.data.inventory.*;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.InvalidDescriptor;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemTagDescriptor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import org.jukeboxmc.Server;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.crafting.recipes.Recipe;
import org.jukeboxmc.crafting.recipes.SmeltingRecipe;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.util.ItemPalette;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
public class CraftingManager {

    private final List<CraftingData> craftingData = new ObjectArrayList<>();
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
            String recipeId = (String) recipe.get( "recipeId" );
            int width = (int) (double) recipe.get( "width" );
            int height = (int) (double) recipe.get( "height" );
            int inputId = (int) (double) recipe.get( "inputId" );
            int inputDamage = (int) (double) recipe.get( "inputDamage" );

            List<ItemDescriptorWithCount> inputItems = new ArrayList<>();
            if ( recipe.containsKey( "inputs" ) ) {
                List<Map<String, Object>> inputs = (List<Map<String, Object>>) recipe.get( "inputs" );
                for ( Map<String, Object> input : inputs ) {
                    if ( input.containsKey( "descriptor" ) ) {
                        List<Map<String, Object>> list = (List<Map<String, Object>>) input.get( "descriptor" );
                        for ( Map<String, Object> map : list ) {
                            String type = (String) map.get( "descriptorType" );

                            if ( type.equalsIgnoreCase( "DEFAULT" ) ) {
                                int id = (int) (double) map.get( "id" );
                                int damage = (int) (double) map.get( "damage" );
                                int count = (int) (double) map.get( "count" );
                                List<Object> canPlace = (List<Object>) map.get( "canPlace" );
                                List<Object> canBreak = (List<Object>) map.get( "canBreak" );
                                int blockingTicks = (int) (double) map.get( "blockingTicks" );
                                int blockRuntimeId = (int) (double) map.get( "blockRuntimeId" );
                                boolean usingNetId = (boolean) map.get( "usingNetId" );
                                int netId = (int) (double) map.get( "netId" );

                                inputItems.add( ItemDescriptorWithCount.fromItem( ItemData.builder()
                                        .id( id )
                                        .damage( damage )
                                        .count( count )
                                        .blockingTicks( blockingTicks )
                                        .blockRuntimeId( blockRuntimeId )
                                        .usingNetId( usingNetId )
                                        .netId( netId )
                                        .build() ) );
                            } else if ( type.equalsIgnoreCase( "INVALID" ) ) {
                                inputItems.add( new ItemDescriptorWithCount( InvalidDescriptor.INSTANCE, 1 ) );
                            } else if ( type.equalsIgnoreCase( "ITEMTAG" ) ) {
                                String itemTag = (String) map.get( "itemTag" );
                                inputItems.add( new ItemDescriptorWithCount( new ItemTagDescriptor( itemTag ), 1 ) );
                            }
                        }

                    }
                }
            }

            List<ItemData> outputItems = new ArrayList<>();
            List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get( "outputs" );
            if ( recipe.containsKey( "outputs" ) ) {
                for ( Map<String, Object> output : outputs ) {
                    int id = (int) (double) output.get( "id" );
                    int damage = (int) (double) output.get( "damage" );
                    int count = (int) (double) output.get( "count" );
                    List<Object> canPlace = (List<Object>) output.get( "canPlace" );
                    List<Object> canBreak = (List<Object>) output.get( "canBreak" );
                    int blockingTicks = (int) (double) output.get( "blockingTicks" );
                    int blockRuntimeId = (int) (double) output.get( "blockRuntimeId" );
                    boolean usingNetId = (boolean) output.get( "usingNetId" );
                    int netId = (int) (double) output.get( "netId" );

                    outputItems.add( ItemData.builder()
                            .id( id )
                            .damage( damage )
                            .count( count )
                            .blockingTicks( blockingTicks )
                            .blockRuntimeId( blockRuntimeId )
                            .usingNetId( usingNetId )
                            .netId( netId )
                            .build() );
                }
            }

            if ( craftingDataType.equals( CraftingDataType.FURNACE ) || craftingDataType.equals( CraftingDataType.FURNACE_DATA ) ) {
                Item input = new Item( ItemPalette.getIdentifier( (short) inputId ), false );
                if ( inputDamage != 32767 ) {
                    input.setMeta( inputDamage );
                }
                Item output = new Item( outputItems.get( 0 ), false );
                if ( output.getMeta() == 32767 ) {
                    output.setMeta( 0 );
                }
                this.smeltingRecipes.add( new SmeltingRecipe( input, output ) );
            }

            UUID uuid = recipe.get( "uuid" ) != null ? UUID.fromString( (String) recipe.get( "uuid" ) ) : null;
            String craftingTag = (String) recipe.get( "craftingTag" );
            int priority = (int) (double) recipe.get( "priority" );
            int networkId = (int) (double) recipe.get( "networkId" );
            this.craftingData.add( new CraftingData( craftingDataType, recipeId, width, height, inputId, inputDamage, inputItems, outputItems, uuid, craftingTag, priority, networkId ) );
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
        Optional<CraftingData> optional = craftingData.stream().max( Comparator.comparing( CraftingData::getNetworkId ) );
        return optional.map( CraftingData::getNetworkId ).orElse( -1 );
    }

    public List<Item> getResultItem( int recipeNetworkId ) {
        Optional<CraftingData> optional = this.craftingData.stream().filter( craftingData -> craftingData.getNetworkId() == recipeNetworkId ).findFirst();
        if ( optional.isPresent() ) {
            CraftingData craftingData = optional.get();
            List<Item> items = new LinkedList<>();
            for ( ItemData output : craftingData.getOutputs() ) {
                items.add( new Item( output, false ) );
            }
            return items;
        }
        return null;
    }

    public SmeltingRecipe getSmeltingRecipe( Item input ) {
        return this.smeltingRecipes.stream().filter( smeltingRecipe -> smeltingRecipe.getInput().getType().equals( input.getType() ) ).findFirst().orElse( null );
    }
}
