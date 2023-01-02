package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.passiv.EntityHuman;
import org.jukeboxmc.event.player.PlayerInteractEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPressurePlate extends Block {

    public BlockPressurePlate( Identifier identifier ) {
        super( identifier );
    }

    public BlockPressurePlate( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block target = world.getBlock( blockPosition );
        if ( target.isTransparent() ) {
            return false;
        }

        this.setRedstoneSignal( 0 );
        return super.placeBlock( player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace );
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
                this.location.getY() + 1f,
                this.location.getZ() + 0.9375f
        );
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }

    private int getRedstoneStrength() {
        AxisAlignedBB boundingBox = this.getBoundingBox();

        for ( Entity entity : this.location.getWorld().getNearbyEntities( boundingBox, this.location.getDimension(), null ) ) {
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
                this.location.getWorld().playSound( this.location, SoundEvent.POWER_OFF );
            } else if ( isPowered && !wasPowered ) {
                this.location.getWorld().playSound( this.location, SoundEvent.POWER_ON );
            }
        }

        if ( isPowered ) {
            this.location.getWorld().scheduleBlockUpdate( this.location, 20 );
        }
    }
}
