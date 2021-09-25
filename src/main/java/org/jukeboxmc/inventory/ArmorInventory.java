package org.jukeboxmc.inventory;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.behavior.ItemArmorBehavior;
import org.jukeboxmc.network.packet.InventoryContentPacket;
import org.jukeboxmc.network.packet.InventorySlotPacket;
import org.jukeboxmc.network.packet.MobArmorEquipmentPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Sound;

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
    public void addViewer( Player player ) {
        super.addViewer( player );
    }

    @Override
    public void setItem( int slot, Item item ) {
        super.setItem( slot, item );

        if ( item instanceof ItemArmorBehavior && this.holder instanceof Player) {
            Player player = (Player) this.holder;

            ArmorInventory armorInventory = player.getArmorInventory();
            MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
            mobArmorEquipmentPacket.setEntityId( this.holderId );
            mobArmorEquipmentPacket.setHelmet( armorInventory.getHelmet() );
            mobArmorEquipmentPacket.setChestplate( armorInventory.getChestplate() );
            mobArmorEquipmentPacket.setLeggings( armorInventory.getLeggings() );
            mobArmorEquipmentPacket.setBoots( armorInventory.getBoots() );

            for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
                if ( onlinePlayer.equals( player ) ) {
                    InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
                    inventorySlotPacket.setWindowId( WindowId.ARMOR_DEPRECATED );
                    inventorySlotPacket.setSlot( slot );
                    inventorySlotPacket.setItem( this.contents[slot] );
                    onlinePlayer.getPlayerConnection().sendPacket( inventorySlotPacket );
                } else {
                    onlinePlayer.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
                }
            }
        }
    }

    @Override
    public void sendContents( Player player ) {
        if ( this.getInventoryHolder().equals( player ) ) {
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
        if ( this.getInventoryHolder().equals( player ) ) {
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
        mobArmorEquipmentPacket.setEntityId( this.getInventoryHolder().getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.contents[0] );
        mobArmorEquipmentPacket.setChestplate( this.contents[1] );
        mobArmorEquipmentPacket.setLeggings( this.contents[2] );
        mobArmorEquipmentPacket.setBoots( this.contents[3] );
        player.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
    }

    public void sendArmorContent( Player player ) {
        MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
        mobArmorEquipmentPacket.setEntityId( this.getInventoryHolder().getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.contents[0] );
        mobArmorEquipmentPacket.setChestplate( this.contents[1] );
        mobArmorEquipmentPacket.setLeggings( this.contents[2] );
        mobArmorEquipmentPacket.setBoots( this.contents[3] );

        for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
            if ( onlinePlayer.equals( player ) ) {
                InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
                inventoryContentPacket.setWindowId( WindowId.ARMOR_DEPRECATED );
                inventoryContentPacket.setItems( this.contents );
                onlinePlayer.getPlayerConnection().sendPacket( inventoryContentPacket );
            } else {
                onlinePlayer.getPlayerConnection().sendPacket( mobArmorEquipmentPacket );
            }
        }
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

    public float getTotalArmorValue() {
        float armorValue = 0;

        for ( Item itemStack : this.contents ) {
            if ( itemStack instanceof ItemArmorBehavior ) {
                armorValue += (float) ( (ItemArmorBehavior) itemStack ).getArmorPoints();
            }
        }
        return armorValue;
    }

    public void damageEvenly( float damage ) {
        damage = damage / 4.0F;

        if ( damage < 1.0F ) {
            damage = 1.0F;
        }

        if ( this.holder instanceof Player ) {
            Player player = (Player) this.holder;

            Item helmet = this.getHelmet();
            if ( helmet != null && !( helmet instanceof ItemAir ) ) {
                if ( helmet.calculateDurability( (int) damage ) ) {
                    this.setHelmet( new ItemAir() );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setHelmet( helmet );
                }
            }

            Item chestplate = this.getChestplate();
            if ( chestplate != null && !( chestplate instanceof ItemAir ) ) {
                if ( chestplate.calculateDurability( (int) damage ) ) {
                    this.setChestplate( new ItemAir() );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setChestplate( chestplate );
                }
            }

            Item leggings = this.getLeggings();
            if ( leggings != null && !( leggings instanceof ItemAir ) ) {
                if ( leggings.calculateDurability( (int) damage ) ) {
                    this.setLeggings( new ItemAir() );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setLeggings( leggings );
                }
            }

            Item boots = this.getBoots();
            if ( boots != null && !( boots instanceof ItemAir ) ) {
                if ( boots.calculateDurability( (int) damage ) ) {
                    this.setBoots( new ItemAir() );
                    player.playSound( Sound.RANDOM_BREAK, 1, 1 );
                } else {
                    this.setBoots( boots );
                }
            }
        }
    }
}
