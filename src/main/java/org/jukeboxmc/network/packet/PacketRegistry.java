package org.jukeboxmc.network.packet;

import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.handler.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketRegistry {

    private static Map<Class, PacketHandler> handler = new HashMap<>();

    static {
        handler.put( LoginPacket.class, new LoginHandler() );
        handler.put( ResourcePackResponsePacket.class, new ResourcePackResponseHandler() );
        handler.put( RequestChunkRadiusPacket.class, new RequestChunkRadiusHandler() );
        handler.put( PlayerMovePacket.class, new PlayerMoveHandler() );
        handler.put( SetLocalPlayerAsInitializedPacket.class, new SetLocalPlayerAsInitializedHandler() );
        handler.put( TextPacket.class, new TextHandler() );
        handler.put( AdventureSettingsPacket.class, new AdventureSettingsHandler() );
        handler.put( LevelSoundEventPacket.class, new LevelSoundEventHandler() );
        handler.put( EmoteListPacket.class, new EmoteListHandler() );
        handler.put( InteractPacket.class, new InteractHandler() );
        handler.put( PlayerActionPacket.class, new PlayerActionHandler() );
        handler.put( AnimatePacket.class, new AnimateHandler() );
        handler.put( ContainerClosePacket.class, new ContainerCloseHandler() );
        handler.put( InventoryTransactionPacket.class, new InventoryTransactionHandler() );
        handler.put( MobEquipmentPacket.class, new MobEquipmentHandler() );
        handler.put( BlockPickRequestPacket.class, new BlockPickRequestHandler() );
        handler.put( BlockEntityDataPacket.class, new BlockEntityDataHandler() );
        handler.put( CommandRequestPacket.class, new CommandRequestHandler() );
    }

    public static PacketHandler getHandler( Class<? extends Packet> clazz ) {
        return PacketRegistry.handler.get( clazz );
    }

    public static Packet getPacket( int packetId ) {
        switch ( packetId ) {
            case Protocol.LOGIN_PACKET:
                return new LoginPacket();
            case Protocol.PLAY_STATUS_PACKET:
                return new PlayStatusPacket();
            case Protocol.RESOURCE_PACKS_INFO_PACKET:
                return new ResourcePacksInfoPacket();
            case Protocol.RESOURCE_PACK_RESPONSE_PACKET:
                return new ResourcePackResponsePacket();
            case Protocol.START_GAME_PACKET:
                return new StartGamePacket();
            case Protocol.BIOME_DEFINITION_LIST_PACKET:
                return new BiomeDefinitionListPacket();
            case Protocol.CREATIVE_CONTENT_PACKET:
                return new CreativeContentPacket();
            case Protocol.REQUEST_CHUNK_RADIUS_PACKET:
                return new RequestChunkRadiusPacket();
            case Protocol.CHUNK_RADIUS_UPDATE_PACKET:
                return new ChunkRadiusUpdatedPacket();
            case Protocol.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET:
                return new NetworkChunkPublisherUpdatePacket();
            case Protocol.DISCONNECT_PACKET:
                return new DisconnectPacket();
            case Protocol.PLAYER_MOVE_PACKET:
                return new PlayerMovePacket();
            case Protocol.TICK_SYNC_PACKET:
                return new TickSyncPacket();
            case Protocol.INTERACT_PACKET:
                return new InteractPacket();
            case Protocol.EMOTE_LIST_PACKET:
                return new EmoteListPacket();
            case Protocol.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET:
                return new SetLocalPlayerAsInitializedPacket();
            case Protocol.TEXT_PACKET:
                return new TextPacket();
            case Protocol.CLIENT_CACHE_STATUS_PACKET:
                return new ClientCacheStatusPacket();
            case Protocol.ADVENTURER_SETTINGS_PACKET:
                return new AdventureSettingsPacket();
            case Protocol.LEVEL_SOUND_EVENT_PACKET:
                return new LevelSoundEventPacket();
            case Protocol.PLAYER_ACTION_PACKET:
                return new PlayerActionPacket();
            case Protocol.ANIMATE_PACKET:
                return new AnimatePacket();
            case Protocol.CONTAINER_CLOSE_PACKET:
                return new ContainerClosePacket();
            case Protocol.INVENTORY_TRANSACTION_PACKET:
                return new InventoryTransactionPacket();
            case Protocol.MOB_EQUIPMENT_PACKET:
                return new MobEquipmentPacket();
            case Protocol.BLOCK_PICK_REQUEST_PACKET:
                return new BlockPickRequestPacket();
            case Protocol.BLOCK_ENTITY_DATA_PACKET:
                return new BlockEntityDataPacket();
            case Protocol.SET_COMMANDS_ENABLED_PACKET:
                return new SetCommandsEnabledPacket();
            case Protocol.COMMAND_REQUEST_PACKET:
                return new CommandRequestPacket();
            case Protocol.SIMULATION_TYPE_PACKET:
                return new SimulationTypePacket();
            case Protocol.NPC_DIALOGUE_PACKET:
                return new NpcDialoguePacket();
            default:
                return null;
        }
    }

}
