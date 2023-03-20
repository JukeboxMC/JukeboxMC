package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    public void handle(@NotNull MobEquipmentPacket packet, Server server, @NotNull Player player ) {
        Inventory inventory = this.getInventory( player, WindowId.getWindowIdById( packet.getContainerId() ) );
        if ( inventory != null ) {
            Item item = inventory.getItem( packet.getHotbarSlot() );
            if ( !item.equals( new Item( packet.getItem(), false) ) ) {
                inventory.sendContents( player );
                return;
            }
            if ( inventory instanceof PlayerInventory playerInventory ) {
                playerInventory.setItemInHandSlot( packet.getHotbarSlot() );
                player.setAction( false );
            }
        }
    }

    private Inventory getInventory(@NotNull Player player, @Nullable WindowId windowId ) {
        return switch (windowId != null ? windowId : WindowId.INVENTORY) {
            case PLAYER -> player.getInventory();
            case CURSOR_DEPRECATED, CURSOR -> player.getCursorInventory();
            case ARMOR_DEPRECATED, ARMOR -> player.getArmorInventory();
            default -> player.getCurrentInventory();
        };
    }
}
