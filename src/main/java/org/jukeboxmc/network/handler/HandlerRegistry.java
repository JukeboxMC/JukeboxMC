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
        packetHandlerMap.put( LoginPacket.class, new LoginHandler() );
        packetHandlerMap.put( ResourcePackClientResponsePacket.class, new ResourcePackClientResponseHandler() );
        packetHandlerMap.put( PacketViolationWarningPacket.class, new PacketViolationWarningHandler() );
        packetHandlerMap.put( RequestChunkRadiusPacket.class, new RequestChunkRadiusHandler() );
        packetHandlerMap.put( ContainerClosePacket.class, new ContainerCloseHandler() );
        packetHandlerMap.put( InteractPacket.class, new InteractHandler() );
        packetHandlerMap.put( TextPacket.class, new TextHandler() );
        packetHandlerMap.put( CommandRequestPacket.class, new CommandRequestHandler() );
        packetHandlerMap.put( MovePlayerPacket.class, new MovePlayerHandler() );
        packetHandlerMap.put( PlayerActionPacket.class, new PlayerActionHandler() );
        packetHandlerMap.put( MobArmorEquipmentPacket.class, new MobArmorEquipmentHandler() );
        packetHandlerMap.put( MobEquipmentPacket.class, new MobEquipmentHandler() );
        packetHandlerMap.put( AnimatePacket.class, new AnimateHandler() );
        packetHandlerMap.put( InventoryTransactionPacket.class, new InventoryTransactionHandler() );
        packetHandlerMap.put( LevelSoundEventPacket.class, new LevelSoundEventHandler() );
        packetHandlerMap.put( AdventureSettingsPacket.class, new AdventureSettingsHandler() );
        packetHandlerMap.put( BlockPickRequestPacket.class, new BlockPickRequestHandler() );
        packetHandlerMap.put( EntityEventPacket.class, new EntityEventHandler() );
        packetHandlerMap.put( RespawnPacket.class, new RespawnHandler() );
        packetHandlerMap.put( TickSyncPacket.class, new TickSyncHandler() );
        packetHandlerMap.put( ModalFormResponsePacket.class, new ModalFormResponseHandler() );
        packetHandlerMap.put( NpcRequestPacket.class, new NpcRequestPacketHandler() );
    }

    public static PacketHandler<? extends BedrockPacket> getPacketHandler( Class<? extends BedrockPacket> clazz ) {
        return packetHandlerMap.get( clazz );
    }
}
