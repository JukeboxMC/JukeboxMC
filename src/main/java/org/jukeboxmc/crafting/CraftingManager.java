package org.jukeboxmc.crafting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Getter;
import org.jukeboxmc.Server;
import org.jukeboxmc.config.Config;
import org.jukeboxmc.config.ConfigType;
import org.jukeboxmc.crafting.data.ContainerMixData;
import org.jukeboxmc.crafting.data.CraftingData;
import org.jukeboxmc.crafting.data.CraftingDataType;
import org.jukeboxmc.crafting.data.PotionMixData;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.utils.BedrockResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Getter
public class CraftingManager {

    private final List<CraftingData> craftingData = new ObjectArrayList<>();
    private final List<PotionMixData> potionMixData = new ObjectArrayList<>();
    private final List<ContainerMixData> containerMixData = new ObjectArrayList<>();

    public CraftingManager() {
        InputStream recipesStream = Server.class.getClassLoader().getResourceAsStream( "recipes.json" );
        if ( recipesStream == null ) {
            throw new AssertionError( "Unable to find recipes.json" );
        }
        int inputsX = 0;
        int outputsX = 0;
        Config config = new Config( recipesStream, ConfigType.JSON );
        List<Map<String, Object>> recipes = (List<Map<String, Object>>) config.getMap().get( "recipes" );
        for ( Map<String, Object> recipe : recipes ) {
            CraftingDataType craftingDataType = CraftingDataType.valueOf( (String) recipe.get( "type" ) );
            String recipeId = (String) recipe.get( "recipeId" );
            int width = (int) (double) recipe.get( "width" );
            int height = (int) (double) recipe.get( "height" );
            int inputId = (int) (double) recipe.get( "inputId" );
            int inputDamage = (int) (double) recipe.get( "inputDamage" );

            List<Item> inputItems = new ArrayList<>();
            if ( recipe.containsKey( "inputs" ) ) {
                inputsX++;
                List<Map<String, Object>> inputs = (List<Map<String, Object>>) recipe.get( "inputs" ); //TODO
                for ( Map<String, Object> input : inputs ) {
                    int id = (int) (double) input.get( "id" );
                    int damage = (int) (double) input.get( "damage" );
                    int count = (int) (double) input.get( "count" );
                    List<Object> canPlace = (List<Object>) input.get( "canPlace" );
                    List<Object> canBreak = (List<Object>) input.get( "canBreak" );
                    int blockingTicks = (int) (double) input.get( "blockingTicks" );
                    int blockRuntimeId = (int) (double) input.get( "blockRuntimeId" );
                    boolean usingNetId = (boolean) input.get( "usingNetId" );
                    int netId = (int) (double) input.get( "netId" );

                    String identifier = BedrockResourceLoader.getItemNameById().get( id );

                    Item item = new Item( identifier, damage, blockRuntimeId );
                    item.setAmount( count );
                    inputItems.add( item );
                }
            }

            List<Item> outputItems = new ArrayList<>();
            List<Map<String, Object>> outputs = (List<Map<String, Object>>) recipe.get( "outputs" ); //TODO
            if ( recipe.containsKey( "outputs" ) ) {
                outputsX++;
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

                    String identifier = BedrockResourceLoader.getItemNameById().get( id );

                    Item item = new Item( identifier, damage, blockRuntimeId );
                    item.setAmount( count );
                    outputItems.add( item );
                }
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
}
