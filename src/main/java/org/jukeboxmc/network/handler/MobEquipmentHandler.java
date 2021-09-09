package org.jukeboxmc.network.handler;

import network.ycc.raknet.packet.Packet;
import org.jukeboxmc.Server;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.MobEquipmentPacket;
import org.jukeboxmc.player.Player;

import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MobEquipmentHandler implements PacketHandler<MobEquipmentPacket> {

    @Override
    public void handle( MobEquipmentPacket packet, Server server, Player player ) {
        Inventory inventory = player.getInventory( Objects.requireNonNull( WindowId.getWindowIdById( packet.getWindowId() ) ) );

        if ( inventory != null ) {
            Item item = inventory.getItem( packet.getHotbarSlot() );

            if ( !item.equals( packet.getItem() ) ) {
                inventory.sendContents( player );
                return;
            }

            if ( inventory instanceof PlayerInventory ) {
                PlayerInventory playerInventory = (PlayerInventory) inventory;
                playerInventory.setItemInHandSlot( packet.getHotbarSlot() );
            }

            player.setAction( false );
        }
    }
}
