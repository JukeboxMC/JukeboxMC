package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class HandlerRegistry {

    private static final Map<Class<? extends BedrockPacket>, PacketHandler<? extends BedrockPacket>> packetHandlerMap = new HashMap<>();

    public static void init() {
        packetHandlerMap.put( PacketViolationWarningPacket.class, new PacketViolationWarningHandler() );
        packetHandlerMap.put( RequestNetworkSettingsPacket.class, new RequestNetworkSettingsHandler() );
        packetHandlerMap.put( LoginPacket.class, new LoginHandler() );
        packetHandlerMap.put( ResourcePackClientResponsePacket.class, new ResourcePackClientResponseHandler() );
        packetHandlerMap.put( RequestChunkRadiusPacket.class, new RequestChunkRadiusHandler() );
        packetHandlerMap.put( MovePlayerPacket.class, new PlayerMoveHandler() );
        packetHandlerMap.put( TextPacket.class, new TextHandler() );
        packetHandlerMap.put( InventoryTransactionPacket.class, new InventoryTransactionHandler() );
        packetHandlerMap.put( ContainerClosePacket.class, new ContainerCloseHandler() );
        packetHandlerMap.put( InteractPacket.class, new InteractHandler() );
        packetHandlerMap.put( ItemStackRequestPacket.class, new ItemStackRequestHandler() );
        packetHandlerMap.put( MobEquipmentPacket.class, new MobEquipmentHandler() );
        packetHandlerMap.put( AnimatePacket.class, new AnimateHandler() );
        packetHandlerMap.put( PlayerActionPacket.class, new PlayerActionHandler() );
        packetHandlerMap.put( RequestAbilityPacket.class, new RequestAbilityHandler() );
        packetHandlerMap.put( LevelSoundEventPacket.class, new LevelSoundEventHandler() );
        packetHandlerMap.put( BlockPickRequestPacket.class, new BlockPickRequestHandler() );
        packetHandlerMap.put( BlockEntityDataPacket.class, new BlockEntityDataHandler() );
        packetHandlerMap.put( CraftingEventPacket.class, new CraftingEventHandler() );
        packetHandlerMap.put( CommandRequestPacket.class, new CommandRequestHandler() );
        packetHandlerMap.put( AnvilDamagePacket.class, new AnvilDamageHandler() );
        packetHandlerMap.put( RespawnPacket.class, new RespawnHandler() );
        packetHandlerMap.put( EntityEventPacket.class, new EntityEventHandler() );
        packetHandlerMap.put( PlayerSkinPacket.class, new PlayerSkinHandler() );
        packetHandlerMap.put( ModalFormResponsePacket.class, new ModalFormResponseHandler() );
        packetHandlerMap.put( NpcRequestPacket.class, new NpcRequestPacketHandler() );
        packetHandlerMap.put( SetLocalPlayerAsInitializedPacket.class, new SetLocalPlayerAsInitializedHandler() );
        packetHandlerMap.put( EmotePacket.class, new EmoteHandler() );
    }

    public static PacketHandler<? extends BedrockPacket> getPacketHandler( Class<? extends BedrockPacket> clazz ) {
        return packetHandlerMap.get( clazz );
    }
}
