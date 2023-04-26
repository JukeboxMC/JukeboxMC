package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.event.block.BlockGrowEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.world.World;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockSugarCane extends Block {

    public BlockSugarCane( Identifier identifier ) {
        super( identifier );
    }

    public BlockSugarCane( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block block = world.getBlock( blockPosition );
        Block replacedBlock = world.getBlock( placePosition );

        if ( replacedBlock.getType().equals( BlockType.WATER ) ) {
            return false;
        }

        if ( world.getBlock( placePosition.subtract( 0, 1, 0 ) ).getType().equals( BlockType.SUGAR_CANE ) ) {
            world.setBlock( blockPosition.add( 0, 1, 0 ), this );
            return true;
        } else if ( block.getType().equals( BlockType.GRASS ) ||
                block.getType().equals( BlockType.DIRT ) ||
                block.getType().equals( BlockType.SAND ) ||
                block.getType().equals( BlockType.PODZOL ) ) {
            Block blockNorth = block.getSide( BlockFace.NORTH );
            Block blockEast = block.getSide( BlockFace.EAST );
            Block blockSouth = block.getSide( BlockFace.SOUTH );
            Block blockWest = block.getSide( BlockFace.WEST );
            if ( ( blockNorth.getType().equals( BlockType.WATER ) ) || ( blockEast.getType().equals( BlockType.WATER ) ) || ( blockSouth.getType().equals( BlockType.WATER ) ) || ( blockWest.getType().equals( BlockType.WATER ) ) ) {
                world.setBlock( placePosition, this );
                return true;
            }
        }
        return false;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        World world = this.location.getWorld();
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
            world.scheduleBlockUpdate( this, 0 );
        }
        if ( updateReason.equals( UpdateReason.SCHEDULED ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( !blockDown.getType().equals( BlockType.SUGAR_CANE ) &&
                    !blockDown.getType().equals( BlockType.GRASS ) &&
                    !blockDown.getType().equals( BlockType.DIRT ) &&
                    !blockDown.getType().equals( BlockType.SAND ) &&
                    !blockDown.getType().equals( BlockType.PODZOL ) ) {
                world.setBlock( this.location, Block.create( BlockType.AIR ) );
                world.dropItem( Item.create( ItemType.SUGAR_CANE ), this.location.add( 0.5f, 0, 0.5f ), null ).spawn();
                world.sendLevelEvent( this.location, LevelEvent.PARTICLE_DESTROY_BLOCK, this.runtimeId );
            }
        }

        if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( blockDown.getType() != BlockType.SUGAR_CANE ) {
                if ( this.getAge() == 15 ) {
                    for ( int y = 1; y < 3; y++ ) {
                        Block block = world.getBlock( new Vector( this.location.getBlockX(), this.location.getBlockY() + y, this.location.getBlockZ() ) );
                        if ( block.getType().equals( BlockType.AIR ) ) {
                            BlockGrowEvent blockGrowEvent = new BlockGrowEvent( block, Block.create( BlockType.SUGAR_CANE ) );
                            Server.getInstance().getPluginManager().callEvent( blockGrowEvent );
                            if ( blockGrowEvent.isCancelled() ) {
                                return -1;
                            }
                            world.setBlock( block.getLocation(), blockGrowEvent.getNewState() );
                        }
                    }
                    this.setAge( 0 );
                } else {
                    this.setAge( this.getAge() + 1 );
                }
            }
        }
        return -1;
    }

    @Override
    public Item toItem() {
        return Item.create( ItemType.SUGAR_CANE );
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
