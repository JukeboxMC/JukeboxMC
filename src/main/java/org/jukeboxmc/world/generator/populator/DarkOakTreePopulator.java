package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves2;
import org.jukeboxmc.block.behavior.BlockLog2;
import org.jukeboxmc.block.data.LeafType2;
import org.jukeboxmc.block.data.LogType2;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DarkOakTreePopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_DARK_OAK_LOG = Block.<BlockLog2>create( BlockType.LOG2 ).setLogType( LogType2.DARK_OAK );
    private final Block BLOCK_DARK_OAK_LEAVES = Block.<BlockLeaves2>create( BlockType.LEAVES2 ).setLeafType( LeafType2.DARK_OAK );
    private final Block BLOCK_DIRT = Block.create( BlockType.DIRT );

    @Override
    public void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( chunkX << 4, ( chunkX << 4 ) + 15 );
            int z = random.nextInt( chunkZ << 4, ( chunkZ << 4 ) + 15 );
            int y = this.getHighestWorkableBlock( chunk, x, z );
            if ( y == -1 ) {
                continue;
            }

            this.place( random, chunkManager, new Vector( x, y, z ) );
        }
    }

    @Override
    public int getHighestWorkableBlock( Chunk chunk, int x, int z ) {
        int y;
        for ( y = 127; y > 0; --y ) {
            BlockType blockType = chunk.getBlock( x, y, z, 0 ).getType();
            if ( blockType == BlockType.DIRT || blockType == BlockType.GRASS ) {
                break;
            } else if ( blockType != BlockType.AIR && blockType != BlockType.SNOW_LAYER ) {
                return -1;
            }
        }

        return ++y;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }

    private void place( Random random, PopulationChunkManager manager, Vector position ) {
        int treeHeight = random.nextInt( 3 ) + random.nextInt( 2 ) + 6;
        int blockX = position.getBlockX();
        int blockY = position.getBlockY();
        int blockZ = position.getBlockZ();

        if ( blockY >= 1 && blockY + treeHeight + 1 < 256 ) {
            Vector blockpos = position.down();
            BlockType state;

            if ( !this.isAnotherTreeNearby( manager, position, treeHeight ) && manager.getBlock( blockpos ).getType() == BlockType.GRASS) {
                this.setDirtAt( manager, blockpos );
                this.setDirtAt( manager, blockpos.east() );
                this.setDirtAt( manager, blockpos.south() );
                this.setDirtAt( manager, blockpos.south().east() );
                BlockFace blockFace = BlockFace.getHorizontal()[random.nextInt( BlockFace.getHorizontal().length )];
                int i1 = treeHeight - random.nextInt( 4 );
                int j1 = 2 - random.nextInt( 3 );
                int x = blockX;
                int z = blockZ;
                int yy = blockY + treeHeight - 1;

                for ( int k2 = 0; k2 < treeHeight; ++k2 ) {
                    if ( k2 >= i1 && j1 > 0 ) {
                        x += blockFace.getOffset().getBlockX();
                        z += blockFace.getOffset().getBlockZ();
                        --j1;
                    }

                    int y = blockY + k2;
                    Vector blockpos1 = new Vector( x, y, z );
                    state = manager.getBlock( blockpos1.getBlockX(), blockpos1.getBlockY(), blockpos1.getBlockZ() ).getType();

                    if ( state.equals( BlockType.AIR ) || state.equals( BlockType.LEAVES ) || state.equals( BlockType.LEAVES2 ) ) {
                        manager.setBlock( blockpos1, BLOCK_DARK_OAK_LOG );
                        manager.setBlock( blockpos1.south(), BLOCK_DARK_OAK_LOG );
                        manager.setBlock( blockpos1.east(), BLOCK_DARK_OAK_LOG );
                        manager.setBlock( blockpos1.south().east(), BLOCK_DARK_OAK_LOG );
                    }
                }

                for ( int xx = -2; xx <= 0; ++xx ) {
                    for ( int zz = -2; zz <= 0; ++zz ) {
                        int l3 = -1;
                        manager.setBlock( x + xx, yy + l3, z + zz, BLOCK_DARK_OAK_LEAVES );
                        manager.setBlock( 1 + x - xx, yy + l3, z + zz, BLOCK_DARK_OAK_LEAVES );
                        manager.setBlock( x + xx, yy + l3, 1 + z - zz, BLOCK_DARK_OAK_LEAVES );
                        manager.setBlock( 1 + x - xx, yy + l3, 1 + z - zz, BLOCK_DARK_OAK_LEAVES );

                        if ( ( xx > -2 || zz > -1 ) && ( xx != -1 || zz != -2 ) ) {
                            l3 = 1;
                            manager.setBlock( x + xx, yy + l3, z + zz, BLOCK_DARK_OAK_LEAVES );
                            manager.setBlock( 1 + x - xx, yy + l3, z + zz, BLOCK_DARK_OAK_LEAVES );
                            manager.setBlock( x + xx, yy + l3, 1 + z - zz, BLOCK_DARK_OAK_LEAVES );
                            manager.setBlock( 1 + x - xx, yy + l3, 1 + z - zz, BLOCK_DARK_OAK_LEAVES );
                        }
                    }
                }

                if ( random.nextBoolean() ) {
                    manager.setBlock( x, yy + 2, z, BLOCK_DARK_OAK_LEAVES );
                    manager.setBlock( x + 1, yy + 2, z, BLOCK_DARK_OAK_LEAVES );
                    manager.setBlock( x + 1, yy + 2, z + 1, BLOCK_DARK_OAK_LEAVES );
                    manager.setBlock( x, yy + 2, z + 1, BLOCK_DARK_OAK_LEAVES );
                }

                for ( int k3 = -3; k3 <= 4; ++k3 ) {
                    for ( int i3 = -3; i3 <= 4; ++i3 ) {
                        if ( ( k3 != -3 || i3 != -3 ) && ( k3 != -3 || i3 != 4 ) && ( k3 != 4 || i3 != -3 ) && ( k3 != 4 || i3 != 4 ) && ( Math.abs( k3 ) < 3 || Math.abs( i3 ) < 3 ) ) {
                            manager.setBlock( x + k3, yy, z + i3, BLOCK_DARK_OAK_LEAVES );
                        }
                    }
                }

                for ( int k3 = -1; k3 <= 2; ++k3 ) {
                    for ( int i3 = -1; i3 <= 2; ++i3 ) {
                        if ( ( k3 < 0 || k3 > 1 || i3 < 0 || i3 > 1 ) && random.nextInt( 3 ) <= 0 ) {
                            int l3 = random.nextInt( 3 ) + 2;

                            for ( int i4 = 0; i4 < l3; ++i4 ) {
                                manager.setBlock( position.getBlockX() + k3, yy - i4 - 1, position.getBlockZ() + i3, BLOCK_DARK_OAK_LOG );
                            }

                            for ( int k4 = -1; k4 <= 1; ++k4 ) {
                                for ( int l4 = -1; l4 <= 1; ++l4 ) {
                                    manager.setBlock( position.getBlockX() + k3 + k4, yy, position.getBlockZ() + i3 + l4, BLOCK_DARK_OAK_LEAVES );
                                }
                            }

                            for ( int k4 = -2; k4 <= 2; ++k4 ) {
                                for ( int l4 = -2; l4 <= 2; ++l4 ) {
                                    if ( Math.abs( k4 ) != 2 || Math.abs( l4 ) != 2 ) {
                                        manager.setBlock( position.getBlockX() + k3 + k4, yy - 1, position.getBlockZ() + i3 + l4, BLOCK_DARK_OAK_LEAVES );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isAnotherTreeNearby( PopulationChunkManager manager, Vector pos, int height ) {
        int i = pos.getBlockX();
        int j = pos.getBlockY();
        int k = pos.getBlockZ();

        for ( int l = 0; l <= height + 1; ++l ) {
            int i1 = 1;

            if ( l == 0 ) {
                i1 = 0;
            }

            if ( l >= height - 1 ) {
                i1 = 2;
            }

            for ( int j1 = -i1; j1 <= i1; ++j1 ) {
                for ( int k1 = -i1; k1 <= i1; ++k1 ) {
                    BlockType type = manager.getBlock( i + j1, j + l, k + k1 ).getType();
                    if ( type == BlockType.LOG || type == BlockType.LOG2 ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void setDirtAt( PopulationChunkManager manager, Vector pos ) {
        if ( manager.getBlock( pos.getBlockX(), pos.getBlockY(), pos.getBlockZ() ).getType() != BlockType.DIRT ) {
            manager.setBlock( pos.getBlockX(), pos.getBlockY(), pos.getBlockZ(), BLOCK_DIRT );
        }
    }
}
