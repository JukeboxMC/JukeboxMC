package org.jukeboxmc.world.generator.object.tree;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves2;
import org.jukeboxmc.block.behavior.BlockLog2;
import org.jukeboxmc.block.data.LeafType2;
import org.jukeboxmc.block.data.LogType2;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SavannaTree {

    private final Block BLOCK_ACACIA_LEAVES = Block.<BlockLeaves2>create( BlockType.LEAVES2 ).setLeafType( LeafType2.ACACIA );
    private final Block BLOCK_ACACIA_LOG = Block.<BlockLog2>create( BlockType.LOG2 ).setLogType( LogType2.ACACIA );
    private final Block BLOCK_DIRT = Block.create( BlockType.DIRT );

    public void create(@NotNull Random random, @NotNull PopulationChunkManager manager, @NotNull Vector position ) {
        int i = random.nextInt( 3 ) + random.nextInt( 3 ) + 5;
        if ( position.getY() >= 1 && position.getY() + i + 1 <= 256 ) {
            Vector down = position.down();
            BlockType block = manager.getBlock( down ).getType();

            if ( ( block == BlockType.GRASS || block == BlockType.DIRT ) && position.getY() < 256 - i - 1 ) {
                manager.setBlock( position.down(), BLOCK_DIRT );
                BlockFace blockFace = BlockFace.getHorizontal()[random.nextInt( BlockFace.getHorizontal().length )];
                int k2 = i - random.nextInt( 4 ) - 1;
                int l2 = 3 - random.nextInt( 3 );
                int i3 = position.getBlockX();
                int j1 = position.getBlockZ();
                int k1 = 0;

                for ( int l1 = 0; l1 < i; ++l1 ) {
                    int i2 = position.getBlockY() + l1;

                    if ( l1 >= k2 && l2 > 0 ) {
                        i3 += blockFace.getOffset().getBlockX();
                        j1 += blockFace.getOffset().getBlockZ();
                        --l2;
                    }

                    Vector blockpos = new Vector( i3, i2, j1 );
                    BlockType material = manager.getBlock( blockpos ).getType();

                    if ( material == BlockType.AIR || material == BlockType.LEAVES2 ) {
                        manager.setBlock( blockpos, BLOCK_ACACIA_LOG );
                        k1 = i2;
                    }
                }

                Vector blockpos2 = new Vector( i3, k1, j1 );
                for ( int j3 = -3; j3 <= 3; ++j3 ) {
                    for ( int i4 = -3; i4 <= 3; ++i4 ) {
                        if ( Math.abs( j3 ) != 3 || Math.abs( i4 ) != 3 ) {
                            this.setLeaves( manager, blockpos2.add( j3, 0, i4 ) );
                        }
                    }
                }

                blockpos2 = blockpos2.up();

                for ( int k3 = -1; k3 <= 1; ++k3 ) {
                    for ( int j4 = -1; j4 <= 1; ++j4 ) {
                        manager.setBlock( blockpos2.add( k3, 0, j4 ), BLOCK_ACACIA_LEAVES );
                    }
                }

                this.setLeaves( manager, blockpos2.east().east() );
                this.setLeaves( manager, blockpos2.west().west() );
                this.setLeaves( manager, blockpos2.south().south() );
                this.setLeaves( manager, blockpos2.north().north() );
                i3 = position.getBlockX();
                j1 = position.getBlockZ();
                BlockFace face1 = BlockFace.getHorizontal()[random.nextInt( BlockFace.getHorizontal().length )];

                if ( face1 != blockFace ) {
                    int l3 = k2 - random.nextInt( 2 ) - 1;
                    int k4 = 1 + random.nextInt( 3 );
                    k1 = 0;

                    for ( int l4 = l3; l4 < i && k4 > 0; --k4 ) {
                        if ( l4 >= 1 ) {
                            int j2 = position.getBlockY() + l4;
                            i3 += face1.getOffset().getBlockX();
                            j1 += face1.getOffset().getBlockZ();
                            Vector blockpos1 = new Vector( i3, j2, j1 );
                            BlockType material1 = manager.getBlock( blockpos1 ).getType();

                            if ( material1 == BlockType.AIR || material1 == BlockType.LEAVES2 ) {
                                manager.setBlock( blockpos1, BLOCK_ACACIA_LOG );
                                k1 = j2;
                            }
                        }

                        ++l4;
                    }

                    if ( k1 > 0 ) {
                        Vector blockpos3 = new Vector( i3, k1, j1 );

                        for ( int i5 = -2; i5 <= 2; ++i5 ) {
                            for ( int k5 = -2; k5 <= 2; ++k5 ) {
                                if ( Math.abs( i5 ) != 2 || Math.abs( k5 ) != 2 ) {
                                    this.setLeaves( manager, blockpos3.add( i5, 0, k5 ) );
                                }
                            }
                        }

                        blockpos3 = blockpos3.up();

                        for ( int j5 = -1; j5 <= 1; ++j5 ) {
                            for ( int l5 = -1; l5 <= 1; ++l5 ) {
                                this.setLeaves( manager, blockpos3.add( j5, 0, l5 ) );
                            }
                        }
                    }
                }
            }
        }
    }

    private void setLeaves(@NotNull PopulationChunkManager manager, @NotNull Vector position ) {
        BlockType type = manager.getBlock( position ).getType();
        if ( type.equals( BlockType.AIR ) || type.equals( BlockType.LEAVES2 ) ) {
            manager.setBlock( position, BLOCK_ACACIA_LEAVES );
        }
    }
}
