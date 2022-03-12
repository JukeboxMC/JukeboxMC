package org.jukeboxmc.network.packet;

import org.jukeboxmc.network.handler.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PacketRegistry {

    private final Map<Class<? extends Packet>, PacketHandler<? extends Packet>> packetHandlerMap = new HashMap<>();

    public PacketRegistry() {
        this.packetHandlerMap.put( LoginPacket.class, new LoginHandler() );
        this.packetHandlerMap.put( ResourcePackResponsePacket.class, new ResourcePackResponseHandler() );
        this.packetHandlerMap.put( BlockEntityDataPacket.class, new BlockEntityDataHandler() );
        this.packetHandlerMap.put( ContainerClosePacket.class, new ContainerCloseHandler() );
        this.packetHandlerMap.put( RequestChunkRadiusPacket.class, new RequestChunkRadiusHandler() );
        this.packetHandlerMap.put( TickSyncPacket.class, new TickSyncHandler() );
        this.packetHandlerMap.put( InteractPacket.class, new InteractHandler() );
        this.packetHandlerMap.put( PlayerMovePacket.class, new PlayerMoveHandler() );
        this.packetHandlerMap.put( TextPacket.class, new TextHandler() );
        this.packetHandlerMap.put( MobEquipmentPacket.class, new MobEquipmentHandler() );
        this.packetHandlerMap.put( InventoryTransactionPacket.class, new InventoryTransactionHandler() );
        this.packetHandlerMap.put( AnimatePacket.class, new AnimateHandler() );
        this.packetHandlerMap.put( EmoteListPacket.class, new EmoteListHandler() );
        this.packetHandlerMap.put( AdventureSettingsPacket.class, new AdventureSettingsHandler() );
        this.packetHandlerMap.put( BlockPickRequestPacket.class, new BlockPickRequestHandler() );
        this.packetHandlerMap.put( CommandRequestPacket.class, new CommandRequestHandler() );
        this.packetHandlerMap.put( PlayerActionPacket.class, new PlayerActionHandler() );
        this.packetHandlerMap.put( SetLocalPlayerAsInitializedPacket.class, new SetLocalPlayerAsInitializedHandler() );
        this.packetHandlerMap.put( EntityEventPacket.class, new EntityEventHandler() );
        this.packetHandlerMap.put( MobArmorEquipmentPacket.class, new MobArmorEquipmentHandler() );
        this.packetHandlerMap.put( RespawnPositionPacket.class, new RespawnPositionHandler() );
        this.packetHandlerMap.put( EntityFallPacket.class, new EntityFallHandler() );
        this.packetHandlerMap.put( LevelSoundEventPacket.class, new LevelSoundEventHandler() );
        this.packetHandlerMap.put( ResourcePackChunkRequestPacket.class, new ResourcePackChunkRequestHandler() );
        this.packetHandlerMap.put( PacketViolationWarningPacket.class, new PacketViolationWarningHandler() );
        this.packetHandlerMap.put( CraftingEventPacket.class, new CraftingEventHandler() );
        this.packetHandlerMap.put( ModalResponsePacket.class, new ModalResponseHandler() );
    }

    public PacketHandler<? extends Packet> getPacketHandler( Class<? extends Packet> clazz ) {
        return this.packetHandlerMap.get( clazz );
    }

    public Packet getPacket( int packetId ) {
        return switch ( packetId ) {
            case Protocol.LOGIN_PACKET -> new LoginPacket();
            case Protocol.CLIENT_CACHE_STATUS_PACKET -> new ClientCacheStatusPacket();
            case Protocol.RESOURCE_PACK_RESPONSE_PACKET -> new ResourcePackResponsePacket();
            case Protocol.BLOCK_ENTITY_DATA_PACKET -> new BlockEntityDataPacket();
            case Protocol.CONTAINER_CLOSE_PACKET -> new ContainerClosePacket();
            case Protocol.REQUEST_CHUNK_RADIUS_PACKET -> new RequestChunkRadiusPacket();
            case Protocol.TICK_SYNC_PACKET -> new TickSyncPacket();
            case Protocol.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET -> new SetLocalPlayerAsInitializedPacket();
            case Protocol.INTERACT_PACKET -> new InteractPacket();
            case Protocol.PLAYER_MOVE_PACKET -> new PlayerMovePacket();
            case Protocol.TEXT_PACKET -> new TextPacket();
            case Protocol.INVENTORY_TRANSACTION_PACKET -> new InventoryTransactionPacket();
            case Protocol.MOB_EQUIPMENT_PACKET -> new MobEquipmentPacket();
            case Protocol.ANIMATE_PACKET -> new AnimatePacket();
            case Protocol.PLAYER_ACTION_PACKET -> new PlayerActionPacket();
            case Protocol.EMOTE_LIST_PACKET -> new EmoteListPacket();
            case Protocol.ADVENTURER_SETTINGS_PACKET -> new AdventureSettingsPacket();
            case Protocol.BLOCK_PICK_REQUEST_PACKET -> new BlockPickRequestPacket();
            case Protocol.COMMAND_REQUEST_PACKET -> new CommandRequestPacket();
            case Protocol.ENTITY_EVENT_PACKET -> new EntityEventPacket();
            case Protocol.RESPAWN_POSITION_PACKET -> new RespawnPositionPacket();
            case Protocol.ENTITY_FALL_PACKET -> new EntityFallPacket();
            case Protocol.LEVEL_SOUND_EVENT_PACKET -> new LevelSoundEventPacket();
            case Protocol.RESOURCE_PACK_CHUNK_REQUEST_PACKET -> new ResourcePackChunkRequestPacket();
            case Protocol.PACKET_VIOLATION_WARNING_PACKET -> new PacketViolationWarningPacket();
            case Protocol.CRAFTING_EVENT_PACKET -> new CraftingEventPacket();
            case Protocol.MODAL_RESPONSE_PACKET -> new ModalResponsePacket();
            default -> null;
        };
    }
}
