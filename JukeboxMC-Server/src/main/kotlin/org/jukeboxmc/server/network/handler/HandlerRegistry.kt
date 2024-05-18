package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.packet.*

class HandlerRegistry {

    companion object {
        private val packetHandlerMap: MutableMap<Class<out BedrockPacket>, PacketHandler<out BedrockPacket>> = mutableMapOf()

        init {
            packetHandlerMap[RequestNetworkSettingsPacket::class.java] = RequestNetworkSettingsHandler()
            packetHandlerMap[LoginPacket::class.java] = LoginHandler()
            packetHandlerMap[ClientToServerHandshakePacket::class.java] = ClientToServerHandshakeHandler()
            packetHandlerMap[ResourcePackClientResponsePacket::class.java] = ResourcePackClientResponseHandler()
            packetHandlerMap[ResourcePackChunkRequestPacket::class.java] = ResourcePackChunkRequestHandler()
            packetHandlerMap[RequestChunkRadiusPacket::class.java] = RequestChunkRadiusHandler()
            packetHandlerMap[MovePlayerPacket::class.java] = MovePlayerHandler()
            packetHandlerMap[TextPacket::class.java] = TextHandler()
            packetHandlerMap[EmoteListPacket::class.java] = EmoteListHandler()
            packetHandlerMap[InteractPacket::class.java] = InteractHandler()
            packetHandlerMap[AnimatePacket::class.java] = AnimateHandler()
            packetHandlerMap[PlayerActionPacket::class.java] = PlayerActionHandler()
            packetHandlerMap[ContainerClosePacket::class.java] = ContainerCloseHandler()
            packetHandlerMap[ItemStackRequestPacket::class.java] = ItemStackRequestHandler()
            packetHandlerMap[MobEquipmentPacket::class.java] = MobEquipmentHandler()
            packetHandlerMap[InventoryTransactionPacket::class.java] = InventoryTransactionHandler()
            packetHandlerMap[BlockPickRequestPacket::class.java] = BlockPickRequestHandler()
            packetHandlerMap[PlayerSkinPacket::class.java] = PlayerSkinHandler()
            packetHandlerMap[BlockEntityDataPacket::class.java] = BlockEntityDataHandler()
            packetHandlerMap[LevelSoundEventPacket::class.java] = LevelSoundEventHandler()
            packetHandlerMap[CommandRequestPacket::class.java] = CommandRequestHandler()
            packetHandlerMap[EntityEventPacket::class.java] = EntityEventHandler()
            packetHandlerMap[RespawnPacket::class.java] = RespawnHandler()
            packetHandlerMap[ModalFormResponsePacket::class.java] = ModalFormResponseHandler()
            packetHandlerMap[PlayerAuthInputPacket::class.java] = PlayerAuthInputHandler()
        }

        fun getPacketHandler(clazz: Class<out BedrockPacket?>?): PacketHandler<out BedrockPacket>? {
            return packetHandlerMap[clazz]
        }
    }

}