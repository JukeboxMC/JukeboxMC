package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.inventory.ArmorInventory;
import org.jukeboxmc.network.packet.MobArmorEquipmentPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MobArmorEquipmentHandler implements PacketHandler<MobArmorEquipmentPacket> {

    @Override
    public void handle( MobArmorEquipmentPacket packet, Server server, Player player ) {
        ArmorInventory armorInventory = player.getArmorInventory();
        armorInventory.setHelmet( packet.getHelmet() );
        armorInventory.setChestplate( packet.getChestplate() );
        armorInventory.setLeggings( packet.getLeggings() );
        armorInventory.setBoots( packet.getBoots() );
    }
}
