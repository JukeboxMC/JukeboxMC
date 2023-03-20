package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.item.behavior.ItemArmor;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Sound;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ArmorInventory extends ContainerInventory {

    public ArmorInventory( InventoryHolder holder ) {
        super( holder, 4 );
    }

    @Override
    public Player getInventoryHolder() {
        return (Player) this.holder;
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.ARMOR;
    }

    @Override
    public void sendContents(@NotNull Player player ) {
        if ( this.getInventoryHolder().equals( player ) ) {
            InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
            inventoryContentPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
            inventoryContentPacket.setContents( this.getItemDataContents() );
            player.getPlayerConnection().sendPacket( inventoryContentPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    @Override
    public void sendContents(int slot, @NotNull Player player ) {
        if ( this.getInventoryHolder().equals( player ) ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
            inventorySlotPacket.setSlot( slot );
            inventorySlotPacket.setItem( this.content[slot].toItemData() );
            player.getPlayerConnection().sendPacket( inventorySlotPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    @Override
    public void setItem(int slot, @NotNull Item item, boolean sendContent ) {
        super.setItem( slot, item, sendContent );

        if ( this.holder instanceof Player player ) {
            ArmorInventory armorInventory = player.getArmorInventory();
            MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
            mobArmorEquipmentPacket.setRuntimeEntityId( this.holderId );
            mobArmorEquipmentPacket.setHelmet( armorInventory.getHelmet().toItemData() );
            mobArmorEquipmentPacket.setChestplate( armorInventory.getChestplate().toItemData() );
            mobArmorEquipmentPacket.setLeggings( armorInventory.getLeggings().toItemData() );
            mobArmorEquipmentPacket.setBoots( armorInventory.getBoots().toItemData() );

            for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
                if ( onlinePlayer.equals( player ) ) {
                    InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
                    inventorySlotPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
                    inventorySlotPacket.setSlot( slot );
                    inventorySlotPacket.setItem( this.content[slot].toItemData() );
                    onlinePlayer.getPlayerConnection().sendPacket( inventorySlotPacket );
                    onlinePlayer.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
                } else {
                    onlinePlayer.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
                }
            }
        }
    }

    private void sendMobArmor(@NotNull Player player ) {
        MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
        mobArmorEquipmentPacket.setRuntimeEntityId( this.getInventoryHolder().getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.content[0].toItemData() );
        mobArmorEquipmentPacket.setChestplate( this.content[1].toItemData() );
        mobArmorEquipmentPacket.setLeggings( this.content[2].toItemData() );
        mobArmorEquipmentPacket.setBoots( this.content[3].toItemData() );
        player.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
    }

    public Item getHelmet() {
        return this.content[0];
    }

    public void setHelmet(@NotNull Item item ) {
        this.setItem( 0, item );
    }

    public Item getChestplate() {
        return this.content[1];
    }

    public void setChestplate(@NotNull Item item ) {
        this.setItem( 1, item );
    }

    public Item getLeggings() {
        return this.content[2];
    }

    public void setLeggings(@NotNull Item item ) {
        this.setItem( 2, item );
    }

    public Item getBoots() {
        return this.content[3];
    }

    public void setBoots(@NotNull Item item ) {
        this.setItem( 3, item );
    }

    public float getTotalArmorValue() {
        float armorValue = 0;

        for ( Item itemStack : this.content ) {
            if ( itemStack instanceof ItemArmor itemArmor ) {
                armorValue += (float) itemArmor.getArmorPoints();
            }
        }
        return armorValue;
    }

    public void damageEvenly( float damage ) {
        damage = damage / 4.0F;

        if ( damage < 1.0F ) {
            damage = 1.0F;
        }

        if ( this.holder instanceof Player player ) {
            Item helmet = this.getHelmet();
            if ( helmet != null && !( helmet.getType().equals( ItemType.AIR ) ) ) {
                if ( helmet.calculateDurability( (int) damage ) ) {
                    this.setHelmet( Item.create( ItemType.AIR ) );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setHelmet( helmet );
                }
            }

            Item chestplate = this.getChestplate();
            if ( chestplate != null && !( chestplate.getType().equals( ItemType.AIR ) ) ) {
                if ( chestplate.calculateDurability( (int) damage ) ) {
                    this.setChestplate( Item.create( ItemType.AIR ) );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setChestplate( chestplate );
                }
            }

            Item leggings = this.getLeggings();
            if ( leggings != null && !( leggings.getType().equals( ItemType.AIR ) ) ) {
                if ( leggings.calculateDurability( (int) damage ) ) {
                    this.setLeggings( Item.create( ItemType.AIR ) );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setLeggings( leggings );
                }
            }

            Item boots = this.getBoots();
            if ( boots != null && !( boots.getType().equals( ItemType.AIR ) ) ) {
                if ( boots.calculateDurability( (int) damage ) ) {
                    this.setBoots( Item.create( ItemType.AIR ) );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setBoots( boots );
                }
            }
        }
    }
}
