package org.jukeboxmc.entity.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerPickupItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.AddItemEntityPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.TakeEntityItemPacket;
import org.jukeboxmc.player.Player;

import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityItem extends Entity {

    private Item item;
    private long pickupDelay;
    private boolean isReset;
    private Player playerHasThrown;

    public EntityItem() {
        this.gravity = 0.04f;
    }

    @Override
    public void update( long currentTick ) {
        if ( this.closed ) {
            return;
        }
        if ( !this.isImmobile() ) {
            if ( !this.isOnGround() ) {
                this.velocity = this.velocity.subtract( 0, this.gravity, 0 );
            }

            this.checkObstruction( this.location.getX(), this.location.getY(), this.location.getZ() );
            this.move( this.velocity );

            float friction = 1 - this.drag;

            if ( this.onGround && ( Math.abs( this.velocity.getX() ) > 0.00001 || Math.abs( this.velocity.getZ() ) > 0.00001 ) ) {
                friction *= 0.6f;
            }

            this.velocity = this.velocity.multiply( friction, 1 - this.drag, friction );

            if ( this.onGround ) {
                this.velocity = this.velocity.multiply( 0, -0.5f, 0 );
            }

            this.updateMovement();
        }

        if ( this.isCollided && !this.isReset &&  this.velocity.squaredLength() < 0.01f ) {
            this.setVelocity( Vector.zero() );
            this.isReset = true;
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
    public Packet createSpawnPacket() {
        AddItemEntityPacket addItemEntityPacket = new AddItemEntityPacket();
        addItemEntityPacket.setEntityId( this.entityId );
        addItemEntityPacket.setRuntimeEntityId( this.entityId );
        addItemEntityPacket.setItem( this.item );
        addItemEntityPacket.setVector( this.location );
        addItemEntityPacket.setVelocity( this.velocity );
        addItemEntityPacket.setMetadata( this.metadata );
        return addItemEntityPacket;
    }

    @Override
    public void onCollideWithPlayer( Player player ) {
        if ( Server.getInstance().getCurrentTick() > this.pickupDelay && !this.closed ) {
            PlayerPickupItemEvent playerPickupItemEvent = new PlayerPickupItemEvent( player, this.item );
            Server.getInstance().getPluginManager().callEvent( playerPickupItemEvent );
            if ( playerPickupItemEvent.isCancelled() ) {
                return;
            }

            TakeEntityItemPacket takeEntityItemPacket = new TakeEntityItemPacket();
            takeEntityItemPacket.setEntityId( player.getEntityId() );
            takeEntityItemPacket.setEntityItemId( this.entityId );
            Server.getInstance().broadcastPacket( takeEntityItemPacket );

            this.despawn();
            player.getInventory().addItem( this.item );
            player.getInventory().sendContents( player );
        }
    }

    public Item getItem() {
        return this.item.clone();
    }

    public void setItem( Item item ) {
        this.item = item;
    }

    public float getGravity() {
        return this.gravity;
    }

    public long getPickupDelay() {
        return this.pickupDelay;
    }

    public void setPickupDelay( long duration, TimeUnit timeUnit ) {
        this.pickupDelay = Server.getInstance().getCurrentTick() + timeUnit.toMillis( duration );
    }

    public Player getPlayerHasThrown() {
        return this.playerHasThrown;
    }

    public void setPlayerHasThrown( Player playerHasThrown ) {
        this.playerHasThrown = playerHasThrown;
    }
}
