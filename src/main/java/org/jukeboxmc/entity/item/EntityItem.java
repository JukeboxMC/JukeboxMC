package org.jukeboxmc.entity.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.player.PlayerPickupItemEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.network.packet.AddItemEntityPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityItem extends Entity {

    private Item item;
    private long pickupDelay;

    public EntityItem() {
        this.lastUpdate = Server.getInstance().getCurrentTick();
    }

    @Override
    public void update( long currentTick ) {
        if ( !this.isImmobile() ) {
            long tickDiff = currentTick - this.lastUpdate;
            if ( tickDiff <= 0 && !this.justCreated ) {
                return;
            }

            this.lastUpdate = currentTick;
            this.justCreated = false;

            if ( this.pickupDelay > 0 && this.pickupDelay < 32767 ) {
                this.pickupDelay -= tickDiff;
                if ( this.pickupDelay < 0 ) {
                    this.pickupDelay = 0;
                }
            }

            if ( !this.isOnGround() ) {
                this.velocity = this.velocity.subtract( 0, this.getGravity(), 0 );
            }

            this.moveEntity( this.velocity.getX(), this.velocity.getY(), this.velocity.getZ() );

            float friction = 1 - this.getDrag();
            if ( this.isOnGround && ( Math.abs( this.velocity.getX() ) > 0.00001 || Math.abs( this.velocity.getZ() ) > 0.00001 ) ) {
                friction *= 0.6f;
            }

            this.velocity = this.velocity.multiply( friction, 1 - this.getDrag(), friction );
            if ( this.isOnGround ) {
                this.velocity.setY( this.velocity.getY() * -0.5f );
            }
            this.updateMovement();
        }
    }

    public String getName() {
        return "EntityItem";
    }

    @Override
    public String getEntityType() {
        return "minecraft:item";
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
        if ( this.pickupDelay <= 0 ) {
            PlayerPickupItemEvent playerPickupItemEvent = new PlayerPickupItemEvent( player, this.item );
            Server.getInstance().getPluginManager().callEvent( playerPickupItemEvent );
            if ( playerPickupItemEvent.isCancelled() ) {
                return;
            }
            player.sendMessage( "Pickup: " + playerPickupItemEvent.getItem().getName() );
        }
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    public float getDrag() {
        return 0.02f;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem( Item item ) {
        this.item = item;
    }

    public long getPickupDelay() {
        return this.pickupDelay;
    }

    public void setPickupDelay( long pickupDelay ) {
        this.pickupDelay = pickupDelay;
    }
}
