package org.jukeboxmc.network.handler;

import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.inventory.WindowId;
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

        Inventory inventory = player.getInventory( WindowId.getWindowIdById( mobEquipmentPacket.getWindowId() ) );

        if ( inventory != null ) {
            Item item = inventory.getItem( mobEquipmentPacket.getHotbarSlot() );

            if ( !item.equals( mobEquipmentPacket.getItem() ) ) {
                inventory.sendContents( player );
                return;
            }

            if ( inventory instanceof PlayerInventory ) {
                PlayerInventory playerInventory = (PlayerInventory) inventory;
                playerInventory.setItemInHandSlot( mobEquipmentPacket.getHotbarSlot() );
            }

            player.setAction( false );
        }
    }
}
