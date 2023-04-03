package org.jukeboxmc.entity.item;

import org.cloudburstmc.protocol.bedrock.packet.AddItemEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.TakeItemEntityPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityMoveable;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.event.player.PlayerPickupItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityItem extends EntityMoveable {

    private Item item;
    private long pickupDelay;
    private boolean isReset;
    private Player playerHasThrown;

    @Override
    public void update( long currentTick ) {
        super.update( currentTick );
        if ( this.isClosed() ) {
            return;
        }
        if ( !this.isImmobile() ) {
            BlockType blockTypeLayer0 = this.location.getWorld().getBlock( this.location.getBlockX(), (int) this.boundingBox.getMaxY(), this.location.getBlockZ(), 0, this.location.getDimension() ).getType();
            if ( blockTypeLayer0.equals( BlockType.FLOWING_WATER ) || blockTypeLayer0.equals( BlockType.WATER ) ) {
                this.velocity.setY( this.velocity.getY() - this.getGravity() * -0.015f );
            } else {
                this.velocity.setY( this.velocity.getY() - this.getGravity() );
            }

            this.checkObstruction( this.location.getX(), this.location.getY(), this.location.getZ() );
            this.move( this.velocity );

            double friction = 1 - this.getDrag();
            if ( this.onGround && ( Math.abs( this.velocity.getX() ) > 0.00001 || Math.abs( this.velocity.getZ() ) > 0.00001 ) ) {
                friction *= 0.6f;
            }

            this.velocity.setX( (float) ( this.velocity.getX() * friction ) );
            this.velocity.setY( this.velocity.getY() * ( 1 - this.getDrag() ) );
            this.velocity.setZ( (float) ( this.velocity.getZ() * friction ) );

            if ( this.onGround ) {
                this.velocity.setY( this.velocity.getY() * -0.5f );
            }

            this.updateMovement();
        }

        if ( this.isCollided && !this.isReset && this.velocity.squaredLength() < 0.01f ) {
            this.setVelocity( Vector.zero() );
            this.isReset = true;
        }

        if ( ( this.age >= TimeUnit.MINUTES.toMillis( 5 ) / 50 ) || this.location.getY() <= -64 ) {
            this.close();
        }
    }

    @Override
    public String getName() {
        return "EntityItem";
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Override
    public EntityType getType() {
        return EntityType.ITEM;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:item" );
    }

    @Override
    public AddItemEntityPacket createSpawnPacket() {
        AddItemEntityPacket addItemEntityPacket = new AddItemEntityPacket();
        addItemEntityPacket.setRuntimeEntityId( this.entityId );
        addItemEntityPacket.setUniqueEntityId( this.entityId );
        addItemEntityPacket.setItemInHand( this.item.toItemData() );
        addItemEntityPacket.setPosition( this.location.toVector3f() );
        addItemEntityPacket.setMotion( this.velocity.toVector3f() );
        addItemEntityPacket.getMetadata().putAll( this.metadata.getEntityDataMap() );
        return addItemEntityPacket;
    }

    @Override
    public void onCollideWithPlayer( Player player ) {
        if ( Server.getInstance().getCurrentTick() > this.pickupDelay && !this.isClosed() && !player.isDead() ) {
            PlayerPickupItemEvent playerPickupItemEvent = new PlayerPickupItemEvent( player, this.item );
            Server.getInstance().getPluginManager().callEvent( playerPickupItemEvent );
            if ( playerPickupItemEvent.isCancelled() || !player.getInventory().canAddItem( playerPickupItemEvent.getItem() ) ) {
                return;
            }

            TakeItemEntityPacket takeItemEntityPacket = new TakeItemEntityPacket();
            takeItemEntityPacket.setRuntimeEntityId( player.getEntityId() );
            takeItemEntityPacket.setItemRuntimeEntityId( this.entityId );
            Server.getInstance().broadcastPacket( takeItemEntityPacket );

            this.close();
            player.getInventory().addItem( playerPickupItemEvent.getItem() );
            player.getInventory().sendContents( player );
        }
    }

    @Override
    public boolean canCollideWith( Entity entity ) {
        return false;
    }

    public Item getItem() {
        return this.item.clone();
    }

    public void setItem( Item item ) {
        this.item = item;
    }

    public long getPickupDelay() {
        return this.pickupDelay;
    }

    public void setPickupDelay( long duration, TimeUnit timeUnit ) {
        this.pickupDelay = Server.getInstance().getCurrentTick() + timeUnit.toMillis( duration ) / 50;
    }

    public Player getPlayerHasThrown() {
        return this.playerHasThrown;
    }

    public void setPlayerHasThrown( Player playerHasThrown ) {
        this.playerHasThrown = playerHasThrown;
    }
}
