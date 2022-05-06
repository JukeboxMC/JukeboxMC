package org.jukeboxmc.inventory;

import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.item.behavior.ItemArmorBehavior;
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

        if ( this.holder instanceof Player player ) {

            ArmorInventory armorInventory = player.getArmorInventory();
            MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
            mobArmorEquipmentPacket.setRuntimeEntityId( this.holderId );
            mobArmorEquipmentPacket.setHelmet( armorInventory.getHelmet().toNetwork() );
            mobArmorEquipmentPacket.setChestplate( armorInventory.getChestplate().toNetwork() );
            mobArmorEquipmentPacket.setLeggings( armorInventory.getLeggings().toNetwork() );
            mobArmorEquipmentPacket.setBoots( armorInventory.getBoots().toNetwork() );

            for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
                if ( onlinePlayer.equals( player ) ) {
                    InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
                    inventorySlotPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
                    inventorySlotPacket.setSlot( slot );
                    inventorySlotPacket.setItem( this.contents[slot].toNetwork() );
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
            inventoryContentPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
            inventoryContentPacket.setContents( this.getItemDataContents() );
            player.sendPacket( inventoryContentPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    @Override
    public void sendContents( int slot, Player player ) {
        if ( this.getInventoryHolder().equals( player ) ) {
            InventorySlotPacket inventorySlotPacket = new InventorySlotPacket();
            inventorySlotPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
            inventorySlotPacket.setSlot( slot );
            inventorySlotPacket.setItem( this.contents[slot].toNetwork() );
            player.sendPacket( inventorySlotPacket );
        } else {
            this.sendMobArmor( player );
        }
    }

    private void sendMobArmor( Player player ) {
        MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
        mobArmorEquipmentPacket.setRuntimeEntityId( this.getInventoryHolder().getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.contents[0].toNetwork() );
        mobArmorEquipmentPacket.setChestplate( this.contents[1].toNetwork() );
        mobArmorEquipmentPacket.setLeggings( this.contents[2].toNetwork() );
        mobArmorEquipmentPacket.setBoots( this.contents[3].toNetwork() );
        player.sendPacket( mobArmorEquipmentPacket );
    }

    public void sendArmorContent( Player player ) {
        MobArmorEquipmentPacket mobArmorEquipmentPacket = new MobArmorEquipmentPacket();
        mobArmorEquipmentPacket.setRuntimeEntityId( this.getInventoryHolder().getEntityId() );
        mobArmorEquipmentPacket.setHelmet( this.contents[0].toNetwork() );
        mobArmorEquipmentPacket.setChestplate( this.contents[1].toNetwork() );
        mobArmorEquipmentPacket.setLeggings( this.contents[2].toNetwork() );
        mobArmorEquipmentPacket.setBoots( this.contents[3].toNetwork() );

        for ( Player onlinePlayer : Server.getInstance().getOnlinePlayers() ) {
            if ( onlinePlayer.equals( player ) ) {
                InventoryContentPacket inventoryContentPacket = new InventoryContentPacket();
                inventoryContentPacket.setContainerId( WindowId.ARMOR_DEPRECATED.getId() );
                inventoryContentPacket.setContents( this.getItemDataContents() );
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

        if ( this.holder instanceof Player player ) {

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
