package org.jukeboxmc.world.generator.object.tree;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.behavior.BlockVine;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SmallJungleTree {

    private final Block BLOCK_LOG = Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.JUNGLE );
    private final Block BLOCK_LEAVES = Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.JUNGLE );
    private final Block BLOCK_VINE = Block.<BlockVine>create( BlockType.VINE );
    private final Block BLOCK_DIRT = Block.create( BlockType.DIRT );

    public void create(@NotNull Random random, @NotNull PopulationChunkManager manager, @NotNull Vector position ) {
        int i = random.nextInt( 4 + random.nextInt( 7 ) ) + 3;
        boolean flag = true;

        if ( position.getY() >= 1 && position.getY() + i + 1 <= 256 ) {
            for ( int j = position.getBlockY(); j <= position.getY() + 1 + i; ++j ) {
                int k = 1;

                if ( j == position.getY() ) {
                    k = 0;
                }

                if ( j >= position.getY() + 1 + i - 2 ) {
                    k = 2;
                }

                Vector pos2 = new Vector( 0, 0, 0 );

                for ( int l = position.getBlockZ() - k; l <= position.getX() + k && flag; ++l ) {
                    for ( int i1 = position.getBlockZ() - k; i1 <= position.getZ() + k && flag; ++i1 ) {
                        if ( j >= 0 && j < 256 ) {
                            pos2.setVector( l, j, i1 );
                            if ( !this.canPlace( manager.getBlock( pos2 ).getType() ) ) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if ( flag ) {
                Vector down = position.down();
                BlockType block = manager.getBlock( down ).getType();

                if ( ( block == BlockType.GRASS || block == BlockType.DIRT || block == BlockType.FARMLAND ) && position.getY() < 256 - i - 1 ) {
                    manager.setBlock( down, BLOCK_DIRT );

                    for ( int i3 = position.getBlockY() - 3 + i; i3 <= position.getY() + i; ++i3 ) {
                        int i4 = i3 - ( position.getBlockY() + i );
                        int j1 = 1 - i4 / 2;

                        for ( int k1 = position.getBlockX() - j1; k1 <= position.getX() + j1; ++k1 ) {
                            int l1 = k1 - position.getBlockX();

                            for ( int i2 = position.getBlockZ() - j1; i2 <= position.getZ() + j1; ++i2 ) {
                                int j2 = i2 - position.getBlockZ();

                                if ( Math.abs( l1 ) != j1 || Math.abs( j2 ) != j1 || random.nextInt( 2 ) != 0 && i4 != 0 ) {
                                    Vector blockpos = new Vector( k1, i3, i2 );
                                    BlockType id = manager.getBlock( blockpos ).getType();

                                    if ( id == BlockType.AIR || id == BlockType.LEAVES || id == BlockType.VINE ) {
                                        manager.setBlock( blockpos, BLOCK_LEAVES );
                                    }
                                }
                            }
                        }
                    }

                    for ( int j3 = 0; j3 < i; ++j3 ) {
                        Vector up = position.add( 0, j3, 0 );
                        BlockType id = manager.getBlock( up ).getType();

                        if ( id == BlockType.AIR || id == BlockType.LEAVES || id == BlockType.VINE ) {
                            manager.setBlock( up, BLOCK_LOG );
                            if ( j3 > 0 ) {
                                if ( random.nextInt( 3 ) > 0 && isAirBlock( manager, position.add( -1, j3, 0 ) ) ) {
                                    this.addVine( manager, position.add( -1, j3, 0 ), 8 );
                                }

                                if ( random.nextInt( 3 ) > 0 && isAirBlock( manager, position.add( 1, j3, 0 ) ) ) {
                                    this.addVine( manager, position.add( 1, j3, 0 ), 2 );
                                }

                                if ( random.nextInt( 3 ) > 0 && isAirBlock( manager, position.add( 0, j3, -1 ) ) ) {
                                    this.addVine( manager, position.add( 0, j3, -1 ), 1 );
                                }

                                if ( random.nextInt( 3 ) > 0 && isAirBlock( manager, position.add( 0, j3, 1 ) ) ) {
                                    this.addVine( manager, position.add( 0, j3, 1 ), 4 );
                                }
                            }
                        }
                    }
                }

                for ( int k3 = position.getBlockY() - 3 + i; k3 <= position.getY() + i; ++k3 ) {
                    int j4 = k3 - ( position.getBlockY() + i );
                    int k4 = 2 - j4 / 2;
                    Vector pos2 = new Vector( 0, 0, 0 );

                    for ( int l4 = position.getBlockX() - k4; l4 <= position.getX() + k4; ++l4 ) {
                        for ( int i5 = position.getBlockZ() - k4; i5 <= position.getZ() + k4; ++i5 ) {
                            pos2.setVector( l4, k3, i5 );

                            if ( manager.getBlock( pos2 ).getType() == BlockType.LEAVES ) {
                                Vector blockpos2 = pos2.west();
                                Vector blockpos3 = pos2.east();
                                Vector blockpos4 = pos2.north();
                                Vector blockpos1 = pos2.south();

                                if ( random.nextInt( 4 ) == 0 && manager.getBlock( blockpos2 ).getType() == BlockType.AIR ) {
                                    this.addHangingVine( manager, blockpos2, 8 );
                                }

                                if ( random.nextInt( 4 ) == 0 && manager.getBlock( blockpos3 ).getType() == BlockType.AIR ) {
                                    this.addHangingVine( manager, blockpos3, 2 );
                                }

                                if ( random.nextInt( 4 ) == 0 && manager.getBlock( blockpos4 ).getType() == BlockType.AIR ) {
                                    this.addHangingVine( manager, blockpos4, 1 );
                                }

                                if ( random.nextInt( 4 ) == 0 && manager.getBlock( blockpos1 ).getType() == BlockType.AIR ) {
                                    this.addHangingVine( manager, blockpos1, 4 );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addHangingVine(@NotNull PopulationChunkManager manager, @NotNull Vector position, int value ) {
        this.addVine( manager, position, value );
        int i = 4;

        for ( position = position.down(); i > 0 && manager.getBlock( position ).getType() == BlockType.AIR; --i ) {
            this.addVine( manager, position, value );
            position = position.down();
        }
    }

    private void addVine(@NotNull PopulationChunkManager manager, @NotNull Vector position, @NotNull BlockFace blockFace ) {
        manager.setBlock( position, ( (BlockVine) BLOCK_VINE ).setVineDirection( 1 << blockFace.getHorizontalIndex() ) );
    }

    private void addVine(@NotNull PopulationChunkManager manager, @NotNull Vector position, int value ) {
        manager.setBlock( position, ( (BlockVine) BLOCK_VINE.clone() ).setVineDirection( value ) );
    }

    private boolean canPlace(@NotNull BlockType blockType ) {
        return blockType.equals( BlockType.AIR ) ||
                blockType.equals( BlockType.LEAVES ) ||
                blockType.equals( BlockType.GRASS ) ||
                blockType.equals( BlockType.DIRT ) ||
                blockType.equals( BlockType.LOG ) ||
                blockType.equals( BlockType.LOG2 ) ||
                blockType.equals( BlockType.SAPLING ) ||
                blockType.equals( BlockType.VINE );
    }

    private boolean isAirBlock(@NotNull PopulationChunkManager manager, @NotNull Vector position ) {
        return manager.getBlock( position ).getType().equals( BlockType.AIR );
    }
}
