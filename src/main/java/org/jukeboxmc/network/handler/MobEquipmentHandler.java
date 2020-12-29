package org.jukeboxmc.network.handler;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.MobEquipmentPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MobEquipmentHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        MobEquipmentPacket mobEquipmentPacket = (MobEquipmentPacket) packet;
        Item item = mobEquipmentPacket.getItem();
        byte hotbarSlot = (byte) mobEquipmentPacket.getHotbarSlot();

        if ( item != null ) {
            player.getInventory().setItemInHandSlot( hotbarSlot );
        } else {

        }
    }
}
