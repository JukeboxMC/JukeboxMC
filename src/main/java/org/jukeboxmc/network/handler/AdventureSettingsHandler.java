package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.event.player.PlayerToggleFlyEvent;
import org.jukeboxmc.network.packet.AdventureSettingsPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AdventureSettingsHandler implements PacketHandler<AdventureSettingsPacket> {

    @Override
    public void handle( AdventureSettingsPacket packet, Server server,  Player player ) {
        AdventureSettings adventureSettings = new AdventureSettings( packet.getFlags(), packet.getFlags2() );

        if ( player.getAdventureSettings().isFlying() != adventureSettings.isFlying() ) {
            PlayerToggleFlyEvent playerToggleFlyEvent = new PlayerToggleFlyEvent( player, adventureSettings.isFlying() );
            playerToggleFlyEvent.setCancelled( !player.getAdventureSettings().isCanFly() );
            server.getPluginManager().callEvent( playerToggleFlyEvent );

            AdventureSettings playerAdventureSettings = player.getAdventureSettings();
            playerAdventureSettings.setFlying( playerToggleFlyEvent.isCancelled() ? player.getAdventureSettings().isFlying() : adventureSettings.isFlying() );
            playerAdventureSettings.update();
        }
    }
}
