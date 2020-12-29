package org.jukeboxmc.inventory;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.passiv.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.network.packet.MobEquipmentPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerInventory extends ContainerInventory {

    private int itemInHandSlot;

    public PlayerInventory( Entity holder ) {
        super( holder, 36 );
    }

    @Override
    public WindowType getWindowType() {
        return WindowType.INVENTORY;
    }

    @Override
    public void sendContents( Player player ) {
        super.sendContents( player );
    }

    @Override
    public void sendContents( int slot, Player player, boolean sendContents ) {
        if ( sendContents ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setWindowId( WindowId.PLAYER );
            inventorySlotPacket.setSlot( slot );
            inventorySlotPacket.setItem( this.contents[slot] );
            player.getPlayerConnection().sendPacket( inventorySlotPacket );
            System.out.println( "SENDING" );
        }
    }

    public Item getItemInHand() {
        Item content = this.contents[this.itemInHandSlot];
        return content != null ? content : ItemType.AIR.getItem();
    }

    public int getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    public void setItemInHandSlot( int itemInHandSlot ) {
        this.itemInHandSlot = itemInHandSlot;
    }

    public MobEquipmentPacket updateItemInHand( EntityHuman entityHuman ) {
        MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
        mobEquipmentPacket.setEntityId( entityHuman.getEntityId() );
        mobEquipmentPacket.setItem( this.getItemInHand() );
        mobEquipmentPacket.setWindowId( WindowId.PLAYER.getId() );
        mobEquipmentPacket.setHotbarSlot( this.itemInHandSlot );
        mobEquipmentPacket.setInventarSlot( this.itemInHandSlot );
        return mobEquipmentPacket;
    }
}
