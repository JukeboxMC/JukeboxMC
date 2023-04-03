package org.jukeboxmc.entity.projectile;

import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLiquid;
import org.jukeboxmc.entity.EntityType;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityFishingHook extends EntityProjectile {

    private boolean isReset;

    @Override
    public void update( long currentTick ) {
        super.update( currentTick );

        if ( this.shooter.isDead() || ( (Player) this.shooter ).getInventory().getItemInHand().getType() != ItemType.FISHING_ROD ) {
            this.close();
            if ( this.shooter instanceof Player player) {
                player.setEntityFishingHook( null );
            }
        }

        if ( this.isCollided && this.isInsideLiquid() ) {
            if ( !this.velocity.equals( new Vector( 0, 0.1f, 0 ) ) ) {
                this.setVelocity( new Vector( 0, 0.1f, 0 ) );
            }
        } else if ( this.isCollided ) {
            if ( !this.isReset && this.velocity.squaredLength() < 0.0025 ) {
                this.setVelocity( Vector.zero() );
                this.isReset = true;
            }
        }
    }

    @Override
    public BedrockPacket createSpawnPacket() {
        this.metadata.setLong( EntityDataTypes.OWNER_EID, this.shooter != null ? this.shooter.getEntityId() : -1 );
        return super.createSpawnPacket();
    }

    @Override
    public String getName() {
        return "Fishing Hook";
    }

    @Override
    public float getWidth() {
        return 0.2f;
    }

    @Override
    public float getHeight() {
        return 0.2f;
    }

    @Override
    public float getGravity() {
        return 0.3f;
    }

    @Override
    public float getDrag() {
        return 0.1f;
    }

    @Override
    public EntityType getType() {
        return EntityType.FISHING_HOOK;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.fromString( "minecraft:fishing_hook" );
    }

    public boolean isInsideLiquid() {
        Location eyeLocation = this.getLocation().add( 0, this.getEyeHeight(), 0 );
        Block block = eyeLocation.getBlock();
        if ( block.getType().equals( BlockType.WATER ) || block.getType().equals( BlockType.FLOWING_WATER ) ) {
            float yLiquid = (float) ( block.getLocation().getY() + 1 + ( ( (BlockLiquid) block ).getLiquidDepth() - 0.12 ) );
            return eyeLocation.getY() < yLiquid;
        }

        return false;
    }
}
