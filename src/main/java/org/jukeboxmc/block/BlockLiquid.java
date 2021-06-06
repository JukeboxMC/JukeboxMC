package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.block.type.UpdateReason;
import org.jukeboxmc.event.block.BlockFromToEvent;
import org.jukeboxmc.item.ItemWoodenSword;
import org.jukeboxmc.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LucGamesYT, PowerNukkit
 * @version 1.0
 */
public abstract class BlockLiquid extends Block {

    public int adjacentSources = 0;

    private final byte CAN_FLOW_DOWN = 1;
    private final byte CAN_FLOW = 0;
    private final byte BLOCKED = -1;

    private Map<Long, Byte> flowCostVisited = new HashMap<>();

    public BlockLiquid( String identifier ) {
        super( identifier );
    }

    @Override
    public long onUpdate( UpdateReason updateReason ) {
        if ( updateReason == UpdateReason.NORMAL ) {
            if ( usesWaterLogging() && layer > 0 ) {
                Block layer0 = this.world.getBlock( this.location, 0 );
                if ( layer0.getBlockType() == BlockType.AIR ) {
                    this.world.setBlock( this.location, new BlockAir(), 1, false );
                    this.world.setBlock( this.location, this, 0, false );
                } else if ( layer0 instanceof BlockWaterlogable && ( ( (BlockWaterlogable) layer0 ).getWaterloggingLevel() <= 0 || ( (BlockWaterlogable) layer0 ).getWaterloggingLevel() == 1 && getLiquidDepth() > 0 ) ) {
                    this.world.setBlock( this.location, new BlockAir(), 1, true );
                }
            }
            this.world.scheduleBlockUpdate( this.location, this.getTickRate() );
            return 0;
        } else if ( updateReason == UpdateReason.SCHEDULED ) {
            int decay = this.getFlowDecay( this );
            int multiplier = this.getFlowDecayPerBlock();
            if ( decay > 0 ) {
                int smallestFlowDecay = -100;
                this.adjacentSources = 0;
                smallestFlowDecay = this.getSmallestFlowDecay( this.world.getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() - 1 ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.world.getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() + 1 ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.world.getBlock( (int) this.location.getX() - 1, (int) this.location.getY(), (int) this.location.getZ() ), smallestFlowDecay );
                smallestFlowDecay = this.getSmallestFlowDecay( this.world.getBlock( (int) this.location.getX() + 1, (int) this.location.getY(), (int) this.location.getZ() ), smallestFlowDecay );
                int newDecay = smallestFlowDecay + multiplier;
                if ( newDecay >= 8 || smallestFlowDecay < 0 ) {
                    newDecay = -1;
                }
                int topFlowDecay = this.getFlowDecay( this.world.getBlock( (int) this.location.getX(), (int) this.location.getY() + 1, (int) this.location.getZ() ) );
                if ( topFlowDecay >= 0 ) {
                    newDecay = topFlowDecay | 0x08;
                }
                if ( this.adjacentSources >= 2 && this instanceof BlockWater ) {
                    Block bottomBlock = this.world.getBlock( (int) this.location.getX(), (int) this.location.getY() - 1, (int) this.location.getZ() );
                    if ( bottomBlock.isSolid() ) {
                        newDecay = 0;
                    } else if ( bottomBlock instanceof BlockWater && ( (BlockWater) bottomBlock ).getLiquidDepth() == 0 ) {
                        newDecay = 0;
                    } else {
                        bottomBlock = bottomBlock.getWorld().getBlock( this.location, 1 );
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
                        to = new BlockAir();
                    } else {
                        to = getBlock( decay );
                    }
                    BlockFromToEvent event = new BlockFromToEvent( this, to );
                    world.getServer().getPluginManager().callEvent( event );
                    if ( !event.isCancelled() ) {
                        this.world.setBlock( this.location, event.getBlockTo(), layer, true );
                        if ( !decayed ) {
                            this.world.scheduleBlockUpdate( this.location, this.getTickRate() );
                        }
                    }
                }
            }
            if ( decay >= 0 ) {
                Block bottomBlock = this.world.getBlock( (int) this.location.getX(), (int) this.location.getY() - 1, (int) this.location.getZ() );
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
                            this.flowIntoBlock( this.world.getBlock( (int) this.location.getX() - 1, (int) this.location.getY(), (int) this.location.getZ() ), adjacentDecay );
                        }
                        if ( flags[1] ) {
                            this.flowIntoBlock( this.world.getBlock( (int) this.location.getX() + 1, (int) this.location.getY(), (int) this.location.getZ() ), adjacentDecay );
                        }
                        if ( flags[2] ) {
                            this.flowIntoBlock( this.world.getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() - 1 ), adjacentDecay );
                        }
                        if ( flags[3] ) {
                            this.flowIntoBlock( this.world.getBlock( (int) this.location.getX(), (int) this.location.getY(), (int) this.location.getZ() + 1 ), adjacentDecay );
                        }
                    }
                }
            }
        }
        return 0;
    }

    public abstract BlockLiquid getBlock( int liquidDepth );

    public boolean usesWaterLogging() {
        return false;
    }

    public int getFlowDecayPerBlock() {
        return 1;
    }

    protected int getFlowDecay( Block block ) {
        if ( block.getBlockType() != this.getBlockType() ) {
            Block layer1 = block.getWorld().getBlock( this.location, 1 );
            if ( layer1.getBlockType() != this.getBlockType() ) {
                return -1;
            } else {
                return ( (BlockLiquid) layer1 ).getLiquidDepth();
            }
        }
        return ( (BlockLiquid) block ).getLiquidDepth();
    }

    private int getSmallestFlowDecay( Block block, int decay ) {
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

    protected boolean canFlowInto( Block block ) {
        if ( usesWaterLogging() ) {
            if ( block.canWaterloggingFlowInto() ) {
                Block blockLayer1 = this.world.getBlock( this.location, 1 );
                return !( block instanceof BlockLiquid && ( (BlockLiquid) block ).getLiquidDepth() == 0 ) && !( blockLayer1 instanceof BlockLiquid && ( (BlockLiquid) blockLayer1 ).getLiquidDepth() == 0 );
            }
        }
        return block.canBeFlowedInto() && !( block instanceof BlockLiquid && ( (BlockLiquid) block ).getLiquidDepth() == 0 );
    }

    protected void flowIntoBlock( Block block, int newFlowDecay ) {
        if ( this.canFlowInto( block ) && !( block instanceof BlockLiquid ) ) {
            if ( usesWaterLogging() ) {
                Block layer1 = this.world.getBlock( this.location, 1 );
                if ( layer1 instanceof BlockLiquid ) {
                    return;
                }

                if ( block instanceof BlockWaterlogable && ( (BlockWaterlogable) block ).getWaterloggingLevel() > 1 ) {
                    block = layer1;
                }
            }

            LiquidFlowEvent event = new LiquidFlowEvent( block, this, newFlowDecay );
            this.world.getServer().getPluginManager().callEvent( event );
            if ( !event.isCancelled() ) {
                if ( block.layer == 0 && block.getBlockType() != BlockType.AIR ) {
                    this.world.breakBlock( null, block.getLocation(), block.getBlockType() == BlockType.WEB ? new ItemWoodenSword() : null );
                }
                this.world.setBlock( block.getLocation(), this.getBlock( newFlowDecay ), block.layer ); //UPDATE TRUE
                this.world.scheduleBlockUpdate( block.getLocation(), this.getTickRate() );
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
            Block block = this.world.getBlock( x, y, z );
            if ( !this.canFlowInto( block ) ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), BLOCKED );
            } else if ( usesWaterLogging() ?
                    this.world.getBlock( x, y - 1, z ).canWaterloggingFlowInto() :
                    this.world.getBlock( x, y - 1, z ).canBeFlowedInto() ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), CAN_FLOW_DOWN );
                flowCost[j] = maxCost = 0;
            } else if ( maxCost > 0 ) {
                this.flowCostVisited.put( Utils.blockHash( x, y, z ), CAN_FLOW );
                flowCost[j] = this.calculateFlowCost( x, y, z, 1, maxCost, j ^ 0x01, j ^ 0x01 );
                maxCost = Math.min( maxCost, flowCost[j] );
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
            int y = blockY;
            int z = blockZ;
            if ( j == 0 ) {
                --x;
            } else if ( j == 1 ) {
                ++x;
            } else if ( j == 2 ) {
                --z;
            } else if ( j == 3 ) {
                ++z;
            }
            long hash = Utils.blockHash( x, y, z );
            if ( !this.flowCostVisited.containsKey( hash ) ) {
                Block blockSide = this.world.getBlock( x, y, z );
                if ( !this.canFlowInto( blockSide ) ) {
                    this.flowCostVisited.put( hash, BLOCKED );
                } else if ( usesWaterLogging() ?
                        this.world.getBlock( x, y - 1, z ).canWaterloggingFlowInto() :
                        this.world.getBlock( x, y - 1, z ).canBeFlowedInto() ) {
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
            int realCost = this.calculateFlowCost( x, y, z, accumulatedCost + 1, maxCost, originOpposite, j ^ 0x01 );
            if ( realCost < cost ) {
                cost = realCost;
            }
        }
        return cost;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canBeFlowedInto() {
        return true;
    }

    public BlockLiquid setLiquidDepth( int value ) {
        return this.setState( "liquid_depth", value );
    }

    public int getLiquidDepth() {
        return this.stateExists( "liquid_depth" ) ? this.getIntState( "liquid_depth" ) : 0;
    }
}
