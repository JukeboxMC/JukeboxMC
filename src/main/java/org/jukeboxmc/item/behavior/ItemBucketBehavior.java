package org.jukeboxmc.item.behavior;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.*;
import org.jukeboxmc.event.player.PlayerBucketEmptyEvent;
import org.jukeboxmc.event.player.PlayerBucketFillEvent;
import org.jukeboxmc.item.*;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBucketBehavior extends Item {

    public ItemBucketBehavior( String identifier ) {
        super( identifier );
    }

    @Override
    public boolean useOnBlock( Player player, Block block, Location placeLocation ) {
        if ( !( block instanceof BlockLiquid ) && !( block instanceof BlockPowderSnow ) ) {
            block = player.getWorld().getBlock( block.getLocation(), 1 );
        }

        if ( block instanceof BlockLiquid || block instanceof BlockPowderSnow ) {
            if ( this.getType() != ItemType.BUCKET ) {
                return false;
            }

            Item item = block instanceof BlockPowderSnow ? new ItemPowderSnowBucket() : block instanceof BlockLava ? new ItemLavaBucket() : new ItemWaterBucket();
            PlayerBucketFillEvent playerBucketFillEvent = new PlayerBucketFillEvent( player, this, item, block );
            Server.getInstance().getPluginManager().callEvent( playerBucketFillEvent );

            if ( playerBucketFillEvent.isCancelled() ) {
                player.getInventory().sendContents( player );
                return false;
            }
            player.getWorld().setBlock( block.getLocation(), new BlockAir(), 0 );

            if ( block instanceof BlockPowderSnow ) {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_FILL_POWDER_SNOW );
            } else if ( block instanceof BlockLava ) {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_FILL_LAVA );
            } else {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_FILL_WATER );
            }

            if ( player.getGameMode() != GameMode.CREATIVE ) {
                if ( this.getAmount() - 1 <= 0 ) {
                    player.getInventory().setItemInHand( playerBucketFillEvent.getItemInHand() );
                } else {
                    Item clone = this.clone();
                    clone.setAmount( this.getAmount() - 1 );
                    player.getInventory().setItemInHand( clone );
                    if ( !player.getInventory().addItem( playerBucketFillEvent.getItemInHand() ) ) {
                        player.getWorld().dropItem( item, player.getLocation(), null );
                    }
                }
            }
            return true;
        } else {
            block = block.getWorld().getBlock( block.getLocation(), 0 );
            Block placedBlock;
            switch ( this.getType() ) {
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
                    if ( block instanceof BlockWaterlogable && this.getType() == ItemType.WATER_BUCKET ) {
                        placedBlock.setLocation( block.getLocation() );
                        placedBlock.setLayer( 1 );
                    } else if ( block instanceof BlockLiquid ) {
                        return false;
                    } else {
                        placedBlock.setLocation( placeLocation );
                    }
            }

            PlayerBucketEmptyEvent playerBucketEmptyEvent = new PlayerBucketEmptyEvent( player, this,
                    new ItemBucket(), block, placedBlock );

            Server.getInstance().getPluginManager().callEvent( playerBucketEmptyEvent );

            if ( playerBucketEmptyEvent.isCancelled() ) {
                player.getInventory().sendContents( player );
                return false;
            }

            if ( placedBlock instanceof BlockPowderSnow ) {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_EMPTY_POWDER_SNOW );
            } else if ( placedBlock instanceof BlockLava ) {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_EMPTY_LAVA );
            } else {
                player.getWorld().playSound( player.getLocation(), SoundEvent.BUCKET_EMPTY_WATER );
            }

            player.getWorld().setBlock( placeLocation, placedBlock, placedBlock.getLayer() );
            if ( placedBlock instanceof BlockLiquid ) {
                player.getWorld().scheduleBlockUpdate( placeLocation, placedBlock.getTickRate() );
            }

            if ( player.getGameMode() != GameMode.CREATIVE ) {
                if ( this.getAmount() - 1 <= 0 ) {
                    player.getInventory().setItemInHand( playerBucketEmptyEvent.getItemInHand() );
                } else {
                    Item clone = this.clone();
                    clone.setAmount( this.getAmount() - 1 );
                    player.getInventory().setItemInHand( clone );
                    if ( !player.getInventory().addItem( playerBucketEmptyEvent.getItemInHand() ) ) {
                        player.getWorld().dropItem( new ItemBucket(), player.getLocation(), null );
                    }
                }
            }
            return true;
        }
    }

}
