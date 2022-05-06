package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.inventory.ArmorInventory;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MobArmorEquipmentHandler implements PacketHandler<MobArmorEquipmentPacket> {

    @Override
    public void handle( MobArmorEquipmentPacket packet, Server server, Player player ) {
        ArmorInventory armorInventory = player.getArmorInventory();
        armorInventory.setHelmet( Item.fromItemData( packet.getHelmet() ) );
        armorInventory.setChestplate( Item.fromItemData( packet.getChestplate() ) );
        armorInventory.setLeggings( Item.fromItemData( packet.getLeggings() ) );
        armorInventory.setBoots( Item.fromItemData( packet.getBoots() ) );
    }
}
