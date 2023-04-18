package org.jukeboxmc.block.behavior;

import org.cloudburstmc.nbt.NbtMap;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.direction.Direction;
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
public class BlockCactus extends Block {

    public BlockCactus( Identifier identifier ) {
        super( identifier );
    }

    public BlockCactus( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public boolean placeBlock( Player player, World world, Vector blockPosition, Vector placePosition, Vector clickedPosition, Item itemInHand, BlockFace blockFace ) {
        Block blockDown = this.getSide( BlockFace.DOWN );
        if ( blockDown.getType().equals( BlockType.SAND ) || blockDown.getType().equals( BlockType.CACTUS ) ) {
            Block blockNorth = this.getSide( BlockFace.NORTH );
            Block blockEast = this.getSide( BlockFace.EAST );
            Block blockSouth = this.getSide( BlockFace.SOUTH );
            Block blockWest = this.getSide( BlockFace.WEST );
            if ( blockNorth.canBeFlowedInto() && blockEast.canBeFlowedInto() && blockSouth.canBeFlowedInto() && blockWest.canBeFlowedInto() ) {
                this.location.getWorld().setBlock( this.location, this );
                return true;
            }
        }
        return false;
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason.equals( UpdateReason.NORMAL ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( !blockDown.getType().equals( BlockType.SAND ) && !blockDown.getType().equals( BlockType.CACTUS ) ) {
                this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ) );
                this.location.getWorld().dropItem( Item.create( ItemType.CACTUS ), this.location.add( 0.5f, 0, 0.5f ), null ).spawn();
            } else {
                for ( Direction direction : Direction.values() ) {
                    Block block = this.getSide( direction );
                    if ( !block.canBeFlowedInto() ) {
                        this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ) );
                        this.location.getWorld().dropItem( Item.create( ItemType.CACTUS ), this.location.add( 0.5f, 0, 0.5f ), null ).spawn();
                    }
                }
            }
        } else if ( updateReason.equals( UpdateReason.RANDOM ) ) {
            Block blockDown = this.getSide( BlockFace.DOWN );
            if ( !blockDown.getType().equals( BlockType.CACTUS ) ) {
                if ( this.getAge() == 15 ) {
                    for ( int y = 0; y < 3; ++y ) {
                        Block block = this.location.getWorld().getBlock( new Vector( this.location.getBlockX(), this.location.getBlockY() + y, this.location.getBlockZ() ) );
                        if ( block.getType().equals( BlockType.AIR ) ) {
                            BlockGrowEvent blockGrowEvent = new BlockGrowEvent( this, Block.create( BlockType.CACTUS ) );
                            Server.getInstance().getPluginManager().callEvent( blockGrowEvent );
                            if ( !blockGrowEvent.isCancelled() ) {
                                this.location.getWorld().setBlock( block.getLocation(), blockGrowEvent.getNewState() );
                            }
                            break;
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

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
