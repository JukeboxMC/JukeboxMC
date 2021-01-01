package org.jukeboxmc.inventory;

import org.jukeboxmc.Server;
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
        }
    }

    @Override
    public void setItem( int slot, Item item ) {
        this.setItem( slot, item, true );
    }

    @Override
    public void setItem( int slot, Item item, boolean sendContents ) {
        super.setItem( slot, item, sendContents );

        if ( slot == this.itemInHandSlot && this.holder instanceof Player ) {
            this.updateItemInHand();
        }
    }

    public Item getItemInHand() {
        Item content = this.contents[this.itemInHandSlot];
        if(content != null) {
            return content;
        } else {
            return ItemType.AIR.getItem();
        }
    }

    public int getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    public void setItemInHandSlot( int itemInHandSlot ) {
        if(itemInHandSlot >= 0 && itemInHandSlot < 9){
            this.itemInHandSlot = itemInHandSlot;
            this.updateItemInHand();
        }
    }

    public MobEquipmentPacket createMobEquipmentPacket( EntityHuman entityHuman ) {
        MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
        mobEquipmentPacket.setEntityId( entityHuman.getEntityId() );
        mobEquipmentPacket.setItem( this.getItemInHand() );
        mobEquipmentPacket.setWindowId( WindowId.PLAYER.getId() );
        mobEquipmentPacket.setHotbarSlot( this.itemInHandSlot );
        mobEquipmentPacket.setInventarSlot( this.itemInHandSlot );
        return mobEquipmentPacket;
    }

    public void updateItemInHand() {
        if ( this.holder instanceof EntityHuman ) {
            EntityHuman entityHuman = (EntityHuman) this.holder;

            MobEquipmentPacket mobEquipmentPacket = this.createMobEquipmentPacket( entityHuman );

            for ( Player onlinePlayers : Server.getInstance().getOnlinePlayers() ) { //Get world players
                onlinePlayers.getPlayerConnection().sendPacket( mobEquipmentPacket );
            }
        }
    }
}
