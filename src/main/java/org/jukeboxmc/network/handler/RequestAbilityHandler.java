package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.data.Ability;
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerToggleFlyEvent;
import org.jukeboxmc.player.AdventureSettings;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RequestAbilityHandler implements PacketHandler<RequestAbilityPacket> {

    @Override
    public void handle( RequestAbilityPacket packet, Server server, Player player ) {

        if ( !player.getAdventureSettings().get( AdventureSettings.Type.ALLOW_FLIGHT ) && packet.getAbility().equals( Ability.FLYING ) ) {
            player.getAdventureSettings().set( AdventureSettings.Type.FLYING, false );
            player.getAdventureSettings().update();
            return;
        }

        if ( player.getAdventureSettings().get( AdventureSettings.Type.ALLOW_FLIGHT ) && packet.getAbility().equals( Ability.FLYING ) ) {
            PlayerToggleFlyEvent playerToggleFlyEvent = new PlayerToggleFlyEvent( player, packet.isBoolValue() );
            playerToggleFlyEvent.setCancelled( !player.getAdventureSettings().get( AdventureSettings.Type.ALLOW_FLIGHT ) );
            server.getPluginManager().callEvent( playerToggleFlyEvent );

            AdventureSettings playerAdventureSettings = player.getAdventureSettings();
            playerAdventureSettings.set( AdventureSettings.Type.FLYING, playerToggleFlyEvent.isCancelled() ? player.getAdventureSettings().get( AdventureSettings.Type.FLYING ) : packet.isBoolValue() );
            playerAdventureSettings.update();
        }
    }
}
