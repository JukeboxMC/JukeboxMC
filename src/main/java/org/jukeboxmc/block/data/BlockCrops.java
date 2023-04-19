package org.jukeboxmc.block.data;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockGrowEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCrops extends Block {

    public BlockCrops( Identifier identifier ) {
        super( identifier );
    }

    public BlockCrops( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( placePosition ).getSide( BlockFace.DOWN );
        if ( block.getType().equals( BlockType.FARMLAND ) ) {
            world.setBlock( this.location, this );
            return true;
        }
        return false;
    }

    @Override
    public boolean interact( Player player, Vector blockPosition, Vector clickedPosition, BlockFace blockFace, Item itemInHand ) {
        if ( itemInHand.getType().equals( ItemType.BONE_MEAL ) ) {
            int maxGrowth = 7;
            int growth = this.getGrowth();
            if ( growth < maxGrowth ) {
                BlockCrops blockCrops = (BlockCrops) this.clone();
                growth += ThreadLocalRandom.current().nextInt( 3 ) + 2;
                blockCrops.setGrowth( growth, false );
                BlockGrowEvent blockGrowEvent = new BlockGrowEvent( this, blockCrops );
                Server.getInstance().getPluginManager().callEvent( blockGrowEvent );
                if ( blockGrowEvent.isCancelled() ) return false;
                this.setGrowth( Math.min( growth, 7 ), true );
                this.location.getWorld().spawnParticle( LevelEvent.PARTICLE_CROP_GROWTH, this.location );

                if ( !player.getGameMode().equals( GameMode.CREATIVE ) ) {
                    player.getInventory().setItemInHand( itemInHand.decreaseAmount() );
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        World world = this.location.getWorld();
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( !blockDown.getType().equals( BlockType.FARMLAND ) ) {
                world.setBlock( this.location, Block.create( BlockType.AIR ) );
                for ( Item item : this.getDrops( Item.create( ItemType.AIR ) ) ) {
                    world.dropItem( item, this.location.add( 0.5f, 0, 0.5f ), null ).spawn();
                }
                return -1;
            }
        } else if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            if ( ThreadLocalRandom.current().nextInt( 2 ) == 1 ) {
                int growth = getGrowth();
                if ( growth < 7 ) {
                    BlockCrops block = (BlockCrops) this.clone();
                    block.setGrowth( growth + 1, false );
                    BlockGrowEvent blockGrowEvent = new BlockGrowEvent( this, block );
                    Server.getInstance().getPluginManager().callEvent( blockGrowEvent );

                    if ( !blockGrowEvent.isCancelled() ) {
                        this.setGrowth( growth + 1, true );
                    }
                }
            }
        }
        return -1;
    }

    public void setGrowth( int value, boolean update ) {
        this.setState( "growth", value, update );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }

    public boolean isFullyGrown() {
        return this.getGrowth() >= 7;
    }

    public Item toSeedsItem() {
        return switch ( this.getType() ) {
            case WHEAT -> Item.create( ItemType.WHEAT_SEEDS );
            case BEETROOT -> Item.create( ItemType.BEETROOT_SEEDS );
            case MELON_STEM -> Item.create( ItemType.MELON_SEEDS );
            case PUMPKIN_STEM -> Item.create( ItemType.PUMPKIN_SEEDS );
            case CARROTS -> Item.create( ItemType.CARROT );
            case POTATOES -> Item.create( ItemType.POTATO );
            default -> this.toItem();
        };
    }
}
