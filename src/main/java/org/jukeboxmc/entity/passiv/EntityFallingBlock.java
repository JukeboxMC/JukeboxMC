package org.jukeboxmc.entity.passiv;

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.entity.EntityMoveable;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityFallingBlock extends EntityMoveable {

    private Block block;

    @Override
    public void update( long currentTick ) {
        if ( this.isClosed() || this.isDead() ) {
            return;
        }
        super.update( currentTick );

        this.velocity.setY( this.velocity.getY() - this.getGravity() );

        this.move( this.velocity );

        float friction = 1 - this.getDrag();

        this.velocity.setX( this.velocity.getX() * friction );
        this.velocity.setY( this.velocity.getY() * ( 1 - this.getDrag() ) );
        this.velocity.setZ( this.velocity.getZ() * friction );

        Vector position = new Vector( this.getX() - 0.5f, this.getY(), this.getZ() - 0.5f ).round();

        if ( this.onGround ) {
            this.close();

            World world = this.getWorld();
            Block replacedBlock = world.getBlock( position );
            if ( replacedBlock.canBeReplaced( null ) ) {
                world.setBlock( position, this.block );
            } else if ( replacedBlock.isTransparent() ) {
                for ( Item drop : this.block.getDrops( null ) ) {
                    world.dropItem( drop, this.location, null ).spawn();
                }
            }
        }
        this.updateMovement();
    }

    @Override
    public String getName() {
        return "Falling Block";
    }

    @Override
    public float getWidth() {
        return 0.98f;
    }

    @Override
    public float getHeight() {
        return 0.98f;
    }

    @Override
    public float getDrag() {
        return 0.02f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Override
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:falling_block" );
    }

    public void setBlock( Block block ) {
        this.block = block;
        this.metadata.setInt( EntityDataTypes.VARIANT, this.block.getRuntimeId() );
    }

    public Block getBlock() {
        return this.block;
    }
}
