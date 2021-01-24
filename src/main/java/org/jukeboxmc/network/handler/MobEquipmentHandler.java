package org.jukeboxmc.network.handler;

import org.jukeboxmc.inventory.PlayerInventory;
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

        PlayerInventory inventory = player.getInventory();
        Item newItem = inventory.getItem( mobEquipmentPacket.getHotbarSlot() );
        if( newItem != null && newItem.equals( mobEquipmentPacket.getItem() ) && newItem.getAmount() == mobEquipmentPacket.getItem().getAmount() ) {
            inventory.setItemInHandSlot( mobEquipmentPacket.getHotbarSlot() );
            player.setAction( false );
        } else {
            inventory.sendItemInHand();
        }
    }
}
