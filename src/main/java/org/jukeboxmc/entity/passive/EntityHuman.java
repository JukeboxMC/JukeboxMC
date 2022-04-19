package org.jukeboxmc.entity.passive;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.event.player.PlayerFoodLevelChangeEvent;
import org.jukeboxmc.inventory.ArmorInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.AddPlayerPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.Device;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.info.GUIScale;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.utils.Utils;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityHuman extends EntityLiving implements InventoryHolder {

    protected UUID uuid;
    protected Skin skin;
    protected DeviceInfo deviceInfo;

    protected int foodTicks;

    protected final PlayerInventory playerInventory;
    protected final ArmorInventory armorInventory;

    public EntityHuman() {
        this.uuid = UUID.randomUUID();
        this.deviceInfo = new DeviceInfo( "Unknown", UUID.randomUUID().toString(), new Random().nextLong(), Device.DEDICATED, GUIScale.CLASSIC );
        this.playerInventory = new PlayerInventory( this, this.entityId );
        this.armorInventory = new ArmorInventory( this, this.entityId );

        this.addAttribute( AttributeType.PLAYER_HUNGER );
        this.addAttribute( AttributeType.PLAYER_SATURATION );
        this.addAttribute( AttributeType.PLAYER_EXHAUSTION );
        this.addAttribute( AttributeType.PLAYER_EXPERIENCE );
        this.addAttribute( AttributeType.PLAYER_LEVEL );
    }

    @Override
    public String getName() {
        return this.getNameTag();
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.8f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HUMAN;
    }

    @Override
    public Packet createSpawnPacket() {
        AddPlayerPacket addPlayerPacket = new AddPlayerPacket();
        addPlayerPacket.setUuid( this.uuid );
        addPlayerPacket.setName( this.getName() );
        addPlayerPacket.setEntityId( this.getEntityId() );
        addPlayerPacket.setRuntimeEntityId( this.getEntityId() );
        addPlayerPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        addPlayerPacket.setX( this.getX() );
        addPlayerPacket.setY( this.getY() );
        addPlayerPacket.setZ( this.getZ() );
        addPlayerPacket.setVelocity( new Vector( 0, 0, 0 ) );
        addPlayerPacket.setPitch( this.getPitch() );
        addPlayerPacket.setHeadYaw( this.getYaw() );
        addPlayerPacket.setYaw( this.getYaw() );
        addPlayerPacket.setGameMode( GameMode.SURVIVAL );
        addPlayerPacket.setItem( ItemType.AIR.getItem() );
        addPlayerPacket.setMetadata( this.getMetadata() );
        addPlayerPacket.setDeviceInfo( this.deviceInfo );
        return addPlayerPacket;
    }

    @Override
    public Entity spawn( Player player ) {
        if ( this != player ) {
            super.spawn( player );

        }
        return this;
    }

    @Override
    public Entity spawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.spawn( player );
        }
        return this;
    }

    @Override
    public void despawn( Player player ) {
        if ( this != player ) {
            super.despawn( player );
        }
    }

    @Override
    public void despawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.despawn( player );
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID( UUID uuid ) {
        this.uuid = uuid;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public void setSkin( Skin skin ) {
        this.skin = skin;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo( DeviceInfo deviceInfo ) {
        this.deviceInfo = deviceInfo;
    }

    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

    public ArmorInventory getArmorInventory() {
        return this.armorInventory;
    }

    // =========== Metadata ===========

    public boolean isSneaking() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SNEAKING );
    }

    public void setSneaking( boolean value ) {
        if ( value != this.isSneaking() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SNEAKING, value ) );
        }
    }

    public boolean isSprinting() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SPRINTING );
    }

    public void setSprinting( boolean value ) {
        if ( value != this.isSprinting() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SPRINTING, value ) );
        }
    }

    public boolean isSwimming() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.SWIMMING );
    }

    public void setSwimming( boolean value ) {
        if ( value != this.isSwimming() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.SWIMMING, value ) );
        }
    }

    public boolean isGliding() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.GLIDING );
    }

    public void setGliding( boolean value ) {
        if ( value != this.isGliding() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.GLIDING, value ) );
        }
    }

    // =========== Attribute ===========

    public boolean isHungry() {
        Attribute attribute = this.getAttribute( AttributeType.PLAYER_HUNGER );
        return attribute.getCurrentValue() < attribute.getMaxValue();
    }

    public int getHunger() {
        return (int) this.getAttributeValue( AttributeType.PLAYER_HUNGER );
    }

    public void setHunger( int value ) {
        Attribute attribute = this.getAttribute( AttributeType.PLAYER_HUNGER );
        float old = attribute.getCurrentValue();
        this.setAttributes( AttributeType.PLAYER_HUNGER, Utils.clamp( value, attribute.getMinValue(), attribute.getMaxValue() ) );
        if ( ( old < 17 && value >= 17 ) ||
                ( old < 6 && value >= 6 ) ||
                ( old > 0 && value == 0 ) ) {
            this.foodTicks = 0;
        }
    }

    public void addHunger( int value ) {
        this.setHunger( this.getHunger() + value );
    }

    public float getSaturation() {
        return this.getAttributeValue( AttributeType.PLAYER_SATURATION );
    }

    public void setSaturation( float value ) {
        Attribute attribute = this.getAttribute( AttributeType.PLAYER_SATURATION );
        this.setAttributes( AttributeType.PLAYER_SATURATION, Utils.clamp( value, attribute.getMinValue(), attribute.getMaxValue() ) );
    }

    public float getExhaustion() {
        return this.getAttributeValue( AttributeType.PLAYER_EXHAUSTION );
    }

    public void setExhaustion( float value ) {
        this.setAttributes( AttributeType.PLAYER_EXHAUSTION, value );
    }

    public float getExperience() {
        return this.getAttributeValue( AttributeType.PLAYER_EXPERIENCE );
    }

    public void setExperience( float value ) {
        this.setAttributes( AttributeType.PLAYER_EXPERIENCE, value );
    }

    public float getLevel() {
        return this.getAttributeValue( AttributeType.PLAYER_LEVEL );
    }

    public void setLevel( float value ) {
        this.setAttributes( AttributeType.PLAYER_LEVEL, value );
    }

    // =========== Other ===========

    public void exhaust( float value ) {
        float exhaustion = this.getExhaustion() + value;

        while ( exhaustion >= 4 ) {
            exhaustion -= 4;

            float saturation = this.getSaturation();
            if ( saturation > 0 ) {
                saturation = Math.max( 0, saturation - 1 );
                this.setSaturation( saturation );
            } else {
                int hunger = this.getHunger();
                if ( hunger > 0 ) {
                    if ( this instanceof Player ) {
                        Player player = (Player) this;
                        PlayerFoodLevelChangeEvent playerFoodLevelChangeEvent = new PlayerFoodLevelChangeEvent( player, hunger, saturation );
                        Server.getInstance().getPluginManager().callEvent( playerFoodLevelChangeEvent );
                        if ( playerFoodLevelChangeEvent.isCancelled() ) {
                            player.updateAttributes();
                            return;
                        }
                        this.setHunger( Math.max( 0, playerFoodLevelChangeEvent.getFoodLevel() - 1 ) );
                    } else {
                        this.setHunger( Math.max( 0, hunger - 1 ) );
                    }
                }
            }
        }
        this.setExhaustion( exhaustion );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        EntityHuman that = (EntityHuman) o;
        return this.uuid.equals( that.uuid );
    }

    @Override
    public int hashCode() {
        return Objects.hash( uuid );
    }
}
