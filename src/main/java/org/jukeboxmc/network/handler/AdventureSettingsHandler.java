package org.jukeboxmc.network.handler;

import org.jukeboxmc.entity.adventure.AdventureSettings;
import org.jukeboxmc.network.packet.AdventureSettingsPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class AdventureSettingsHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        AdventureSettingsPacket adventureSettingsPacket = (AdventureSettingsPacket) packet;

        AdventureSettings adventureSettings = new AdventureSettings( adventureSettingsPacket.getFlags(), adventureSettingsPacket.getFlags2() );

        if ( player.getAdventureSettings().isFlying() != adventureSettings.isFlying() ) {
            //PlayerToggleFlyEvent

            AdventureSettings playerAdventureSettings = player.getAdventureSettings();
            playerAdventureSettings.setFlying( adventureSettings.isFlying() );
            playerAdventureSettings.update();
        }
    }
}
