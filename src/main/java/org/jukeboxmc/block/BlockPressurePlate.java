package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPressurePlate extends Block {

    public BlockPressurePlate( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, BlockPosition blockPosition, BlockPosition placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block target = world.getBlock( blockPosition );
        if ( target.isTransparent() ) {
            return false;
        }

        this.setRedstoneSignal( 0 );
        world.setBlock( placePosition, this );
        return true;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.SCHEDULED ) {
            int power = this.getRedstoneSignal();

            if ( power > 0 ) {
                this.updateState( power );
            }
        }
        return -1;
    }

    @Override
    public void enterBlock() {
        this.updateState( this.getRedstoneSignal() );
    }

    @Override
    public void leaveBlock() {
        this.updateState( this.getRedstoneSignal() );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
        this.world.sendBlockUpdate( this );
        this.getChunk().setBlock( this.location, this.layer, this.runtimeId );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }

    private int getRedstoneStrength() {
        AxisAlignedBB boundingBox = this.getBoundingBox();

        for ( Entity entity : this.world.getNearbyEntities( boundingBox ) ) {
            if ( entity instanceof EntityHuman ) {
                return 15;
            }
        }

        return 0;
    }

    private void updateState( int oldSignal ) {
        int redstoneStrength = this.getRedstoneStrength();
        boolean wasPowered = oldSignal > 0;
        boolean isPowered = redstoneStrength > 0;

        if ( oldSignal != redstoneStrength ) {
            this.setRedstoneSignal( redstoneStrength );
            if ( !isPowered && wasPowered ) {
                this.world.playSound( this.location, LevelSound.POWER_OFF );
            } else if ( isPowered && !wasPowered ) {
                this.world.playSound( this.location, LevelSound.POWER_ON );
            }
        }

        if ( isPowered ) {
            this.world.scheduleBlockUpdate( this.location, 20 );
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return new AxisAlignedBB(
                this.location.getX() + 0.0625f,
                this.location.getY(),
                this.location.getZ() + 0.0625f,
                this.location.getX() + 0.9375f,
                this.location.getY() + 0.0625f,
                this.location.getZ() + 0.9375f
        );
    }
}
