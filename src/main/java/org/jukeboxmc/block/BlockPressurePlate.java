package org.jukeboxmc.block;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemToolType;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockPressurePlate extends BlockWaterlogable {

    public BlockPressurePlate( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemIndHand, BlockFace blockFace ) {
        Block target = world.getBlock( blockPosition );
        if ( target.isTransparent() ) {
            return false;
        }

        this.setRedstoneSignal( 0 );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemIndHand, blockFace );
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
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public void enterBlock( Player player ) {
        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent( player, PlayerInteractEvent.Action.PHYSICAL,
                player.getInventory().getItemInHand(), this );

        Server.getInstance().getPluginManager().callEvent( playerInteractEvent );

        if ( playerInteractEvent.isCancelled() ) {
            return;
        }

        this.updateState( this.getRedstoneSignal() );
    }

    @Override
    public void leaveBlock( Player player ) {
        this.updateState( this.getRedstoneSignal() );
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

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    @Override
    public boolean canBreakWithHand() {
        return false;
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }

    private int getRedstoneStrength() {
        AxisAlignedBB boundingBox = this.getBoundingBox();

        for ( Entity entity : this.world.getNearbyEntities( boundingBox, this.location.getDimension(), null ) ) {
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


}
