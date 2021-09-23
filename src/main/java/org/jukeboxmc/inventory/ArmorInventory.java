package org.jukeboxmc.inventory;

import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.network.packet.MobArmorEquipmentPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ArmorInventory extends ContainerInventory {

    public ArmorInventory( InventoryHolder holder, long holderId ) {
        super( holder, holderId, 4 );
    }

    @Override
    public EntityHuman getInventoryHolder() {
        return (EntityHuman) this.holder;
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.ARMOR;
    }

    @Override
    public void sendContents( Player player ) {
        if ( this.holder instanceof Player ) {
            InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
            inventoryContentPacket.setWindowId( WindowId.ARMOR_DEPRECATED );
            inventoryContentPacket.setItems( this.contents );
            player.getPlayerConnection().sendPacket( inventoryContentPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    @Override
    public void sendContents( int slot, Player player ) {
        if ( this.holder instanceof Player ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setWindowId( WindowId.ARMOR_DEPRECATED );
            inventorySlotPacket.setSlot( slot );
            inventorySlotPacket.setItem( this.contents[slot] );
            player.getPlayerConnection().sendPacket( inventorySlotPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    private void sendMobArmor( Player player ) {
        MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
        mobArmorEquipmentPacket.setEntityId( player.getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.contents[0] );
        mobArmorEquipmentPacket.setChestplate( this.contents[1] );
        mobArmorEquipmentPacket.setLeggings( this.contents[2] );
        mobArmorEquipmentPacket.setBoots( this.contents[3] );
        player.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
    }

    public Item getHelmet() {
        return this.contents[0];
    }

    public void setHelmet( Item item ) {
        this.setItem( 0, item );
    }

    public Item getChestplate() {
        return this.contents[1];
    }

    public void setChestplate( Item item ) {
        this.setItem( 1, item );
    }

    public Item getLeggings() {
        return this.contents[2];
    }

    public void setLeggings( Item item ) {
        this.setItem( 2, item );
    }

    public Item getBoots() {
        return this.contents[3];
    }

    public void setBoots( Item item ) {
        this.setItem( 3, item );
    }
}
