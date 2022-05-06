package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.inventory.Inventory;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.inventory.WindowId;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class MobEquipmentHandler implements PacketHandler<MobEquipmentPacket> {

    @Override
    public void handle( MobEquipmentPacket packet, Server server, Player player ) {
        Inventory inventory = player.getInventory( WindowId.getWindowIdById( packet.getContainerId() ), packet.getHotbarSlot() );
        if ( inventory != null ) {

            Item item = inventory.getItem( packet.getHotbarSlot() );

            if ( !item.equals( Item.fromItemData( packet.getItem() ) ) ) {
                inventory.sendContents( player );
                return;
            }

            if ( inventory instanceof PlayerInventory playerInventory ) {
                playerInventory.setItemInHandSlot( packet.getHotbarSlot() );
                player.setAction( false );
            }
        }
    }
}
