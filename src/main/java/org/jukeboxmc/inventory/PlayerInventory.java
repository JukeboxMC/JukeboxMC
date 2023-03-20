package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.ContainerType;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.entity.passiv.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.player.Player;

import java.util.Objects;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerInventory extends ContainerInventory {

    private int itemInHandSlot;

    public PlayerInventory( InventoryHolder holder ) {
        super( holder, 36 );
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.PLAYER;
    }


    @Override
    public @NotNull ContainerType getWindowTypeId() {
        return ContainerType.INVENTORY;
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public void sendContents(@NotNull Player player ) {
        InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
        if ( player.getCurrentInventory() == this ) {
            inventoryContentPacket.setContainerId( WindowId.OPEN_CONTAINER.getId() );
            inventoryContentPacket.setContents( this.getItemDataContents() );
            player.getPlayerConnection().sendPacket( inventoryContentPacket );
            return;
        }
        inventoryContentPacket.setContainerId( WindowId.PLAYER.getId() );
        inventoryContentPacket.setContents( this.getItemDataContents() );
        player.getPlayerConnection().sendPacket( inventoryContentPacket );
    }

    @Override
    public void sendContents(int slot, @NotNull Player player ) {
        if ( player.getCurrentInventory() != null && player.getCurrentInventory() == this ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setSlot( slot );
            inventorySlotPacket.setItem( this.content[slot].toItemData() );
            inventorySlotPacket.setContainerId( WindowId.OPEN_CONTAINER.getId() );
            player.getPlayerConnection().sendPacket( inventorySlotPacket );
        }

        InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
        inventorySlotPacket.setSlot( slot );
        inventorySlotPacket.setItem( this.content[slot].toItemData() );
        inventorySlotPacket.setContainerId( WindowId.PLAYER.getId() );
        player.getPlayerConnection().sendPacket( inventorySlotPacket );
    }

    @Override
    public void removeViewer(@NotNull Player player ) {
        if ( player != this.holder ) {
            super.removeViewer( player );
        }
    }

    @Override
    public void setItem(int slot, @NotNull Item item ) {
        Item oldItem = this.getItem( slot );
        super.setItem( slot, item );

        if ( slot == this.itemInHandSlot && this.holder instanceof Player player ) {
            oldItem.removeFromHand( player );
            item.addToHand( player );
            this.updateItemInHandForAll();
        }
    }

    public @NotNull Item getItemInHand() {
        Item content = this.content[this.itemInHandSlot];
        return Objects.requireNonNullElseGet( content, () -> Item.create( ItemType.AIR ) );
    }

    public int getItemInHandSlot() {
        return this.itemInHandSlot;
    }

    public void setItemInHandSlot( int itemInHandSlot ) {
        if ( itemInHandSlot >= 0 && itemInHandSlot < 9 ) {
            Item oldItem = this.getItemInHand();
            oldItem.removeFromHand( (Player) this.holder );

            this.itemInHandSlot = itemInHandSlot;

            Item item = this.getItemInHand();
            item.addToHand( (Player) this.holder );

            this.updateItemInHandForAll();
        }
    }

    public void setItemInHand(@NotNull Item itemInHand ) {
        this.setItem( this.itemInHandSlot, itemInHand );
        this.sendItemInHand();
    }

    public @NotNull MobEquipmentPacket createMobEquipmentPacket(@NotNull EntityHuman entityHuman ) {
        MobEquipmentPacket mobEquipmentPacket = new MobEquipmentPacket();
        mobEquipmentPacket.setRuntimeEntityId( entityHuman.getEntityId() );
        mobEquipmentPacket.setItem( this.getItemInHand().toItemData() );
        mobEquipmentPacket.setContainerId( WindowId.PLAYER.getId() );
        mobEquipmentPacket.setHotbarSlot( this.itemInHandSlot );
        mobEquipmentPacket.setInventorySlot( this.itemInHandSlot );
        return mobEquipmentPacket;
    }

    public void sendItemInHand() {
        if ( this.holder instanceof Player player ) {
            player.getPlayerConnection().sendPacket( this.createMobEquipmentPacket( player ) );
            this.sendContents( this.itemInHandSlot, player );
        }
    }

    public void updateItemInHandForAll() {
        if ( this.holder instanceof EntityHuman entityHuman ) {

            MobEquipmentPacket mobEquipmentPacket = this.createMobEquipmentPacket( entityHuman );

            for ( Player onlinePlayers : entityHuman.getWorld().getPlayers() ) {
                if ( onlinePlayers != entityHuman ) {
                    onlinePlayers.getPlayerConnection().sendPacket( mobEquipmentPacket );
                }
            }
        }
    }
}
