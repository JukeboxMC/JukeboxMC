package org.jukeboxmc.network.packet;

import org.jukeboxmc.network.Protocol;
import org.jukeboxmc.network.handler.LoginHandler;
import org.jukeboxmc.network.handler.PacketHandler;
import org.jukeboxmc.network.handler.ResourcePackResponseHandler;

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
            default:
                return null;
        }
    }

}
