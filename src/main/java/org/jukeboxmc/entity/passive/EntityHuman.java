package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.entity.metadata.EntityFlag;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.AddPlayerPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.Device;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.info.GUIScale;
import org.jukeboxmc.player.skin.Skin;

import java.util.Random;
import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityHuman extends EntityLiving implements InventoryHolder {

    private UUID uuid;
    private Skin skin;
    private DeviceInfo deviceInfo;
    private final PlayerInventory playerInventory;

    public EntityHuman() {
        this.uuid = UUID.randomUUID();
        this.deviceInfo = new DeviceInfo( "Unknown", UUID.randomUUID().toString(), new Random().nextLong(), Device.DEDICATED, GUIScale.CLASSIC );
        this.playerInventory = new PlayerInventory( this );
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
    public String getEntityType() {
        return "minecraft:player";
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
        addPlayerPacket.setHeadYaw( this.getHeadYaw() );
        addPlayerPacket.setYaw( this.getYaw() );
        addPlayerPacket.setItem( ItemType.AIR.getItem() );
        addPlayerPacket.setMetadata( this.getMetadata() );
        addPlayerPacket.setDeviceInfo( this.deviceInfo );
        return addPlayerPacket;
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    @Override
    public EntityHuman spawn( Player player ) {
        if ( this != player ) {
            super.spawn( player );
        }
        return this;
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

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID( UUID uuid ) {
        this.uuid = uuid;
    }

    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

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

    public boolean hasAction() {
        return this.metadata.getDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION );
    }

    public void setAction( boolean value ) {
        if ( value != this.hasAction() ) {
            this.updateMetadata( this.metadata.setDataFlag( MetadataFlag.INDEX, EntityFlag.ACTION, value ) );
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

    public boolean isHungry() {
        Attribute attribute = this.getAttribute( AttributeType.HEALTH );
        return attribute.getCurrentValue() < attribute.getMaxValue();
    }

    public float getHunger() {
        return this.getAttributeValue( AttributeType.PLAYER_HUNGER );
    }

    public void setHunger( float value ) {
        if ( value > 20 || value < 0 ) {
            return;
        }
        this.setAttributes( AttributeType.PLAYER_HUNGER, value );
    }

    public float getSaturation() {
        return this.getAttributeValue( AttributeType.PLAYER_SATURATION );
    }

    public void setSaturation( float value ) {
        this.setAttributes( AttributeType.PLAYER_SATURATION, value );
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
}
