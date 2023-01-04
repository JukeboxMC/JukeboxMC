package org.jukeboxmc.entity.passiv;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.packet.AddPlayerPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.event.player.PlayerFoodLevelChangeEvent;
import org.jukeboxmc.inventory.ArmorInventory;
import org.jukeboxmc.inventory.InventoryHolder;
import org.jukeboxmc.inventory.PlayerInventory;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.player.info.Device;
import org.jukeboxmc.player.info.DeviceInfo;
import org.jukeboxmc.player.info.UIProfile;
import org.jukeboxmc.player.skin.Skin;
import org.jukeboxmc.potion.EffectType;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.Utils;

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

    protected final PlayerInventory playerInventory;
    protected final ArmorInventory armorInventory;

    protected int foodTicks;
    private long actionStart = -1;

    public EntityHuman() {
        this.uuid = UUID.randomUUID();
        this.deviceInfo = new DeviceInfo( "Unknown", UUID.randomUUID().toString(), new Random().nextLong(), Device.DEDICATED, UIProfile.CLASSIC );
        this.playerInventory = new PlayerInventory( this );
        this.armorInventory = new ArmorInventory( this );

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
    public EntityType getType() {
        return EntityType.HUMAN;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:player" );
    }

    @Override
    public float getEyeHeight() {
        return 1.62f;
    }

    @Override
    public BedrockPacket createSpawnPacket() {
        AddPlayerPacket addPlayerPacket = new AddPlayerPacket();
        addPlayerPacket.setRuntimeEntityId( this.entityId );
        addPlayerPacket.setUniqueEntityId( this.entityId );
        addPlayerPacket.setUuid( this.uuid );
        addPlayerPacket.setUsername( this.getName() );
        addPlayerPacket.setPlatformChatId( this.deviceInfo.getDeviceId() );
        addPlayerPacket.setPosition( this.location.toVector3f() );
        addPlayerPacket.setMotion( this.velocity.toVector3f() );
        addPlayerPacket.setRotation( Vector3f.from( this.location.getPitch(), this.location.getYaw(), this.location.getYaw() ) );
        addPlayerPacket.setGameType( GameType.SURVIVAL );
        addPlayerPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        addPlayerPacket.setDeviceId( this.deviceInfo.getDeviceId() );
        addPlayerPacket.setHand( this.playerInventory.getItemInHand().toItemData() );
        return addPlayerPacket;
    }

    @Override
    public EntityHuman spawn( Player player ) {
        if ( this != player ) {
            super.spawn( player );
            this.armorInventory.sendContents( player );
        }
        return this;
    }

    @Override
    public EntityHuman spawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.spawn( player );
        }
        return this;
    }

    @Override
    public EntityHuman despawn( Player player ) {
        if ( this != player ) {
            super.despawn( player );
        }
        return this;
    }

    @Override
    public EntityHuman despawn() {
        for ( Player player : this.getWorld().getPlayers() ) {
            this.despawn( player );
        }
        return this;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID( UUID uuid ) {
        this.uuid = uuid;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo( DeviceInfo deviceInfo ) {
        this.deviceInfo = deviceInfo;
    }

    public Skin getSkin() {
        return this.skin;
    }

    public void setSkin( Skin skin ) {
        this.skin = skin;
    }

    public PlayerInventory getInventory() {
        return this.playerInventory;
    }

    public ArmorInventory getArmorInventory() {
        return this.armorInventory;
    }

    // =========== Metadata ===========

    public boolean isSneaking() {
        return this.metadata.getFlag( EntityFlag.SNEAKING );
    }

    public void setSneaking( boolean value ) {
        if ( value != this.isSneaking() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.SNEAKING, value ) );
        }
    }

    public boolean isSprinting() {
        return this.metadata.getFlag( EntityFlag.SPRINTING );
    }

    public void setSprinting( boolean value ) {
        if ( value != this.isSprinting() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.SPRINTING, value ) );
            this.setMovement( value ? this.getMovement() * 1.3f : this.getMovement() / 1.3f );

            if ( this.hasEffect( EffectType.SPEED ) ){
                float movement = this.getMovement();
                this.setMovement( value ? movement * 1.3f : movement );
            }
        }
    }

    public boolean isSwimming() {
        return this.metadata.getFlag( EntityFlag.SWIMMING );
    }

    public void setSwimming( boolean value ) {
        if ( value != this.isSwimming() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.SWIMMING, value ) );
        }
    }

    public boolean isGliding() {
        return this.metadata.getFlag( EntityFlag.GLIDING );
    }

    public void setGliding( boolean value ) {
        if ( value != this.isGliding() ) {
            this.updateMetadata( this.metadata.setFlag( EntityFlag.GLIDING, value ) );
        }
    }

    public long getActionStart() {
        return this.actionStart;
    }

    public boolean hasAction() {
        return this.metadata.getFlag( EntityFlag.USING_ITEM );
    }

    public void setAction( boolean value ) {
        this.updateMetadata( this.metadata.setFlag( EntityFlag.USING_ITEM, value ) );
        if ( value ) {
            this.actionStart = Server.getInstance().getCurrentTick();
        } else {
            this.actionStart = -1;
        }
    }

    public void resetActionStart() {
        this.actionStart = Server.getInstance().getCurrentTick();
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

    public void addExperience( int value, boolean playLevelUpSound ) {
        if (value == 0) return;
        int now = (int) this.getExperience();
        int added = now + value;
        int level = (int) this.getLevel();
        int most = calculateRequireExperience(level);
        while (added >= most) {  //Level Up!
            added = added - most;
            level++;
            most = calculateRequireExperience(level);
        }
        this.setExhaustion( added );
        this.setLevel( level );
        if ( playLevelUpSound ) {
            this.location.getWorld().playSound( this.location, SoundEvent.LEVELUP, Math.min(7, level / 5) << 28 );
        }
    }

    public int calculateRequireExperience( int level ) {
        if ( level >= 30 ) {
            return 112 + ( level - 30 ) * 9;
        } else if ( level >= 15 ) {
            return 37 + ( level - 15 ) * 5;
        } else {
            return 7 + level * 2;
        }
    }

    public void exhaust( float value ) {
        if ( this.hasEffect( EffectType.SATURATION ) ) {
            return;
        }
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
                    if ( this instanceof Player player ) {
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

}
