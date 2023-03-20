package org.jukeboxmc.block.behavior;

import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import org.apache.commons.math3.util.FastMath;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.data.UpdateReason;
import org.jukeboxmc.event.block.BlockFromToEvent;
import org.jukeboxmc.event.block.BlockLiquidFlowEvent;
import org.jukeboxmc.util.Identifier;
import org.jukeboxmc.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class BlockLiquid extends Block {

    public int adjacentSources = 0;

    private final byte CAN_FLOW_DOWN = 1;
    private final byte CAN_FLOW = 0;
    private final byte BLOCKED = -1;

    private final Map<Long, Byte> flowCostVisited = new HashMap<>();

    public BlockLiquid( Identifier identifier ) {
        super( identifier );
    }

    public BlockLiquid( Identifier identifier, NbtMap blockStates ) {
        super( identifier, blockStates );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.NORMAL ) {
            if ( usesWaterLogging() && this.layer > 0 ) {
                Block layer0 = this.location.getWorld().getBlock( this.location, 0 );
                if ( layer0.getType() == BlockType.AIR ) {
                    this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ), 1, this.location.getDimension(), false );
                    this.location.getWorld().setBlock( this.location, this, 0, this.location.getDimension(), false );
                } else if ( layer0 instanceof Waterlogable && ( ( (Waterlogable) layer0 ).getWaterLoggingLevel() <= 0 || ( (Waterlogable) layer0 ).getWaterLoggingLevel() == 1 && getLiquidDepth() > 0 ) ) {
                    this.location.getWorld().setBlock( this.location, Block.create( BlockType.AIR ), 1, this.location.getDimension(), true );
                }
            }
            this.checkForHarden();
            this.location.getWorld().scheduleBlockUpdate( this.location, this.getTickRate() );
            return 0;
        } else if ( updateReason == UpdateReason.SCHEDULED ) {
            int decay = this.getFlowDecay( this );
            int multiplier = this.getFlowDecayPerBlock();
            if ( decay > 0 ) {
                int smallestFlowDecay = -100;
                this.adjacentSources = 0;
                smallestFlowDecay = this.getSmallestFlowDecay( this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() - 1, 0 , this.location.getDimension() ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() + 1, 0 , this.location.getDimension() ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.location.getWorld().getBlock( (int) this.location.getX() - 1, (int) this.location.getY(), (int) this.location.getZ(), 0 , this.location.getDimension() ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.location.getWorld().getBlock( (int) this.location.getX() + 1, (int) this.location.getY(), (int) this.location.getZ(), 0 , this.location.getDimension() ), smallestFlowDecay );
                int newDecay = smallestFlowDecay + multiplier;
                if ( newDecay >= 8 || smallestFlowDecay < 0 ) {
                    newDecay = -1;
                }
                int topFlowDecay = this.getFlowDecay( this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY() + 1, (int) this.location.getZ(), 0 , this.location.getDimension() ) );
                if ( topFlowDecay >= 0 ) {
                    newDecay = topFlowDecay | 0x08;
                }
                if ( this.adjacentSources >= 2 && this instanceof BlockWater ) {
                    Block bottomBlock = this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY() - 1, (int) this.location.getZ(),0 , this.location.getDimension() );
                    if ( bottomBlock.isSolid() ) {
                        newDecay = 0;
                    } else if ( bottomBlock instanceof BlockWater && ( (BlockWater) bottomBlock ).getLiquidDepth() == 0 ) {
                        newDecay = 0;
                    } else {
                        bottomBlock = bottomBlock.getLocation().getWorld().getBlock( location, 1 );
                        if ( bottomBlock instanceof BlockWater && ( (BlockWater) bottomBlock ).getLiquidDepth() == 0 ) {
                            newDecay = 0;
                        }
                    }
                }
                if ( newDecay != decay ) {
                    decay = newDecay;
                    boolean decayed = decay < 0;
                    Block to;
                    if ( decayed ) {
                        to = Block.create( BlockType.AIR );
                    } else {
                        to = getBlock( decay );
                    }
                    BlockFromToEvent event = new BlockFromToEvent( this, to );
                    Server.getInstance().getPluginManager().callEvent( event );
                    if ( !event.isCancelled() ) {
                        this.location.getWorld().setBlock( this.location, event.getBlockTo(), this.layer, this.location.getDimension(), true );
                        if ( !decayed ) {
                            this.location.getWorld().scheduleBlockUpdate( this.location, this.getTickRate() );
                        }
                    }
                }
            }
            if ( decay >= 0 ) {
                Block bottomBlock = this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY() - 1, (int) this.location.getZ(), 0 , this.location.getDimension() );
                this.flowIntoBlock( bottomBlock, decay | 0x08 );
                if ( decay == 0 || !( usesWaterLogging() ? bottomBlock.canWaterloggingFlowInto() : bottomBlock.canBeFlowedInto() ) ) {
                    int adjacentDecay;
                    if ( decay >= 8 ) {
                        adjacentDecay = 1;
                    } else {
                        adjacentDecay = decay + multiplier;
                    }
                    if ( adjacentDecay < 8 ) {
                        boolean[] flags = this.getOptimalFlowDirections();
                        if ( flags[0] ) {
                            this.flowIntoBlock( this.location.getWorld().getBlock( (int) this.location.getX() - 1, (int) this.location.getY(), (int) this.location.getZ(), 0 , this.location.getDimension() ), adjacentDecay );
                        }
                        if ( flags[1] ) {
                            this.flowIntoBlock( this.location.getWorld().getBlock( (int) this.location.getX() + 1, (int) this.location.getY(), (int) this.location.getZ(), 0 , this.location.getDimension() ), adjacentDecay );
                        }
                        if ( flags[2] ) {
                            this.flowIntoBlock( this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() - 1, 0 , this.location.getDimension() ), adjacentDecay );
                        }
                        if ( flags[3] ) {
                            this.flowIntoBlock( this.location.getWorld().getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() + 1, 0 , this.location.getDimension() ), adjacentDecay );
                        }
                    }
                }
                this.checkForHarden();
            }
        }
        return 0;
    }

    public abstract BlockLiquid getBlock( int liquidDepth );

    protected void checkForHarden() {
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }

    public boolean usesWaterLogging() {
        return false;
    }

    public int getFlowDecayPerBlock() {
        return 1;
    }

    protected int getFlowDecay(@NotNull Block block ) {
        if ( block.getType() != this.getType() ) {
            Block layer1 = block.getLocation().getWorld().getBlock( this.location, 1 );
            if ( layer1.getType() != this.getType() ) {
                return -1;
            } else {
                return ( (BlockLiquid) layer1 ).getLiquidDepth();
            }
        }
        return ( (BlockLiquid) block ).getLiquidDepth();
    }

    private int getSmallestFlowDecay(@NotNull Block block, int decay ) {
        int blockDecay = this.getFlowDecay( block );
        if ( blockDecay < 0 ) {
            return decay;
        } else if ( blockDecay == 0 ) {
            ++this.adjacentSources;
        } else if ( blockDecay >= 8 ) {
            blockDecay = 0;
        }
        return ( decay >= 0 && blockDecay >= decay ) ? decay : blockDecay;
    }

    protected boolean canFlowInto(@NotNull Block block ) {
        if ( usesWaterLogging() ) {
            if ( block.canWaterloggingFlowInto() ) {
                Block blockLayer1 = this.location.getWorld().getBlock( this.location, 1 );
                return !( block instanceof BlockLiquid && ( (BlockLiquid) block ).getLiquidDepth() == 0 ) && !( blockLayer1 instanceof BlockLiquid && ( (BlockLiquid) blockLayer1 ).getLiquidDepth() == 0 );
            }
        }
        return block.canBeFlowedInto() && !( block instanceof BlockLiquid && ( (BlockLiquid) block ).getLiquidDepth() == 0 );
    }

    protected void flowIntoBlock(@NotNull Block block, int newFlowDecay) {
        if ( this.canFlowInto( block ) && !( block instanceof BlockLiquid ) ) {
            if ( usesWaterLogging() ) {
                Block layer1 = location.getWorld().getBlock( location, 1 );
                if ( layer1 instanceof BlockLiquid ) {
                    return;
                }

                if ( block instanceof Waterlogable && ( (Waterlogable) block ).getWaterLoggingLevel() > 1 ) {
                    block = layer1;
                }
            }

            BlockLiquidFlowEvent event = new BlockLiquidFlowEvent( block, this, newFlowDecay );
            Server.getInstance().getPluginManager().callEvent( event );
            if ( !event.isCancelled() ) {
                if ( block.getLayer() == 0 && block.getType() != BlockType.AIR ) {
                    //TODO DROP ITEM IF LIQUID IS COLLIDED
                    //this.location.getWorld().breakBlock( null, block.getLocation(), block.getType() == BlockType.WEB ? Item.create( ItemType.WOODEN_SWORD ) : null );
                }
                location.getWorld().setBlock( block.getLocation(), this.getBlock( newFlowDecay ), block.getLayer() );
                location.getWorld().scheduleBlockUpdate( block.getLocation(), this.getTickRate() );
            }
        }
    }

    private boolean[] getOptimalFlowDirections() {
        int[] flowCost = new int[]{
                1000,
                1000,
                1000,
                1000
        };
        int maxCost = 4 / this.getFlowDecayPerBlock();
        for ( int j = 0; j < 4; ++j ) {
            int x = (int) this.location.getX();
            int y = (int) this.location.getY();
            int z = (int) this.location.getZ();
            if ( j == 0 ) {
                --x;
            } else if ( j == 1 ) {
                ++x;
            } else if ( j == 2 ) {
                --z;
            } else {
                ++z;
            }
            Block block = this.location.getWorld().getBlock( x, y, z, 0, this.location.getDimension() );
            if ( !this.canFlowInto( block ) ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), BLOCKED );
            } else if ( usesWaterLogging() ?
                    this.location.getWorld().getBlock( x, y - 1, z, 0, this.location.getDimension() ).canWaterloggingFlowInto() :
                    this.location.getWorld().getBlock( x, y - 1, z, 0, this.location.getDimension() ).canBeFlowedInto() ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), CAN_FLOW_DOWN );
                flowCost[j] = maxCost = 0;
            } else if ( maxCost > 0 ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), CAN_FLOW );
                flowCost[j] = this.calculateFlowCost( x, y, z, 1, maxCost, j ^ 0x01, j ^ 0x01 );
                maxCost = FastMath.min( maxCost, flowCost[j] );
            }
        }
        this.flowCostVisited.clear();
        double minCost = Double.MAX_VALUE;
        for ( int i = 0; i < 4; i++ ) {
            double d = flowCost[i];
            if ( d < minCost ) {
                minCost = d;
            }
        }
        boolean[] isOptimalFlowDirection = new boolean[4];
        for ( int i = 0; i < 4; ++i ) {
            isOptimalFlowDirection[i] = ( flowCost[i] == minCost );
        }
        return isOptimalFlowDirection;
    }

    private int calculateFlowCost( int blockX, int blockY, int blockZ, int accumulatedCost, int maxCost, int originOpposite, int lastOpposite ) {
        int cost = 1000;
        for ( int j = 0; j < 4; ++j ) {
            if ( j == originOpposite || j == lastOpposite ) {
                continue;
            }
            int x = blockX;
            int z = blockZ;
            if ( j == 0 ) {
                --x;
            } else if ( j == 1 ) {
                ++x;
            } else if ( j == 2 ) {
                --z;
            } else {
                ++z;
            }
            long hash = Utils.blockHash( x, blockY, z );
            if ( !this.flowCostVisited.containsKey( hash ) ) {
                Block blockSide = this.location.getWorld().getBlock( x, blockY, z, 0, this.location.getDimension() );
                if ( !this.canFlowInto( blockSide ) ) {
                    this.flowCostVisited.put( hash, BLOCKED );
                } else if ( usesWaterLogging() ?
                        this.location.getWorld().getBlock( x, blockY - 1, z, 0, this.location.getDimension() ).canWaterloggingFlowInto() :
                        this.location.getWorld().getBlock( x, blockY - 1, z, 0, this.location.getDimension() ).canBeFlowedInto() ) {
                    this.flowCostVisited.put( hash, CAN_FLOW_DOWN );
                } else {
                    this.flowCostVisited.put( hash, CAN_FLOW );
                }
            }
            byte status = this.flowCostVisited.get( hash );
            if ( status == BLOCKED ) {
                continue;
            } else if ( status == CAN_FLOW_DOWN ) {
                return accumulatedCost;
            }
            if ( accumulatedCost >= maxCost ) {
                continue;
            }
            int realCost = this.calculateFlowCost( x, blockY, z, accumulatedCost + 1, maxCost, originOpposite, j ^ 0x01 );
            if ( realCost < cost ) {
                cost = realCost;
            }
        }
        return cost;
    }

    protected void liquidCollide( Block result ) {
        BlockFromToEvent event = new BlockFromToEvent( this, result );
        this.location.getWorld().getServer().getPluginManager().callEvent( event );
        if ( event.isCancelled() ) {
            return;
        }
        this.location.getWorld().setBlock( this.location, event.getBlockTo(), 0 );
        this.location.getWorld().playSound( this.location.add( 0.5f, 0.5f, 0.5f ), SoundEvent.FIZZ );
    }

    public int getTickRate() {
        return 10;
    }

    public BlockLiquid setLiquidDepth( int value ) {
        return this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
