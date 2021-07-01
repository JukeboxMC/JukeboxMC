package org.jukeboxmc.item;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.*;
import org.jukeboxmc.event.player.PlayerBucketEmptyEvent;
import org.jukeboxmc.event.player.PlayerBucketFillEvent;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.LevelSound;

public abstract class AbstractItemBucket extends Item {

    public AbstractItemBucket( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean useOnBlock( Player player, Block block, Location placeLocation ) {
        if ( !( block instanceof BlockLiquid ) ) {
            block = player.getWorld().getBlock( block.getLocation(), 1 );
        }

        if ( block instanceof BlockLiquid ) {
            if ( this.getItemType() != ItemType.BUCKET ) {
                return false;
            }

            PlayerBucketFillEvent playerBucketFillEvent = new PlayerBucketFillEvent( player, this,
                    player.getInventory().getItemInHand(), block );

            Server.getInstance().getPluginManager().callEvent( playerBucketFillEvent );

            if ( playerBucketFillEvent.isCancelled() ) {
                return false;
            }

            LevelSound sound;
            Item filledBucket;
            if ( block instanceof BlockWater || block instanceof BlockFlowingWater ) {
                sound = LevelSound.BUCKET_FILL_WATER;
                filledBucket = new ItemWaterBucket();
            } else {
                sound = LevelSound.BUCKET_FILL_LAVA;
                filledBucket = new ItemLavaBucket();
            }

            player.getWorld().setBlock( block.getLocation(), new BlockAir() );
            player.getWorld().playSound( player.getLocation(), sound );
            if ( player.getGameMode() != GameMode.CREATIVE ) {
                player.getInventory().setItemInHand( this.getAmount() == 1 ? new ItemAir() : this.decreaseAmount() );
            }
            if ( !player.getInventory().addItem( filledBucket ) ) {
                //TODO: Drop filledBucket
            }
            return true;
        } else {
            Block placedBlock;
            switch ( this.getItemType() ) {
                case BUCKET:
                case MILK_BUCKET:
                case COD_BUCKET:
                case SALMON_BUCKET:
                case PUFFERFISH_BUCKET:
                case TROPICAL_FISH_BUCKET:
                case AXOLOTL_BUCKET:
                    return false;
                default:
                    placedBlock = this.getBlock();
                    if ( block instanceof BlockWaterlogable && this.getItemType() == ItemType.WATER_BUCKET ) {
                        placedBlock.setLocation( block.getLocation() );
                        placedBlock.setLayer( 1 );
                    } else
                        placedBlock.setLocation( placeLocation );
            }

            PlayerBucketEmptyEvent playerBucketEmptyEvent = new PlayerBucketEmptyEvent( player, this,
                    player.getInventory().getItemInHand(), block, placedBlock );

            Server.getInstance().getPluginManager().callEvent( playerBucketEmptyEvent );

            LevelSound sound;
            if ( placedBlock instanceof BlockWater || placedBlock instanceof BlockFlowingWater ) {
                sound = LevelSound.BUCKET_EMPTY_WATER;
            } else if ( placedBlock instanceof BlockPowderSnow ) {
                sound = LevelSound.BUCKET_EMPTY_POWDER_SNOW;
            } else {
                sound = LevelSound.BUCKET_EMPTY_LAVA;
            }

            player.getWorld().setBlock( placeLocation, placedBlock, placedBlock.getLayer() );
            if ( placedBlock instanceof BlockLiquid ) {
                player.getWorld().scheduleBlockUpdate( placeLocation, placedBlock.getTickRate() );
            }

            player.getWorld().playSound( player.getLocation(), sound );
            if ( player.getGameMode() != GameMode.CREATIVE ) {
                player.getInventory().setItemInHand( this.getAmount() == 1 ? new ItemAir() : this.decreaseAmount() );
            }
            Item emptyBucket = new ItemBucket();
            if ( !player.getInventory().addItem( emptyBucket ) ) {
                //TODO: Drop emptyBucket
            }
            return true;
        }
    }

}
