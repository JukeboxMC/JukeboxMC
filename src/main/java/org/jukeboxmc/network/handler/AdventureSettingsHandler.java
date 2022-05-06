package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.AdventureSetting;
import com.nukkitx.protocol.bedrock.packet.AdventureSettingsPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerToggleFlyEvent;
import org.jukeboxmc.player.AdventureSettings;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AdventureSettingsHandler implements PacketHandler<AdventureSettingsPacket> {

    @Override
    public void handle( AdventureSettingsPacket packet, Server server, Player player ) {

        if ( player.getAdventureSettings().get( AdventureSettings.Type.ALLOW_FLIGHT ) && packet.getSettings().contains( AdventureSetting.FLYING ) ) {
            PlayerToggleFlyEvent playerToggleFlyEvent = new PlayerToggleFlyEvent( player, packet.getSettings().contains( AdventureSetting.FLYING ) );
            playerToggleFlyEvent.setCancelled( !player.getAdventureSettings().get( AdventureSettings.Type.ALLOW_FLIGHT) );
            server.getPluginManager().callEvent( playerToggleFlyEvent );

            AdventureSettings playerAdventureSettings = player.getAdventureSettings();
            playerAdventureSettings.set( AdventureSettings.Type.FLYING, playerToggleFlyEvent.isCancelled() ? player.getAdventureSettings().get( AdventureSettings.Type.FLYING ) : packet.getSettings().contains( AdventureSetting.FLYING ) );
            playerAdventureSettings.update();
        }
    }
}
