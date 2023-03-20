package org.jukeboxmc.world.generator.object.tree;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockLeaves;
import org.jukeboxmc.block.behavior.BlockLog;
import org.jukeboxmc.block.behavior.BlockVine;
import org.jukeboxmc.block.data.LeafType;
import org.jukeboxmc.block.data.LogType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BigJungleTree {

    private final Block BLOCK_LOG = Block.<BlockLog>create( BlockType.LOG ).setLogType( LogType.JUNGLE );
    private final Block BLOCK_LEAVES = Block.<BlockLeaves>create( BlockType.LEAVES ).setLeafType( LeafType.JUNGLE );
    private final Block BLOCK_VINE = Block.create( BlockType.VINE );
    private final Block BLOCK_DIRT = Block.create( BlockType.DIRT );

    private final int baseHeight;
    private final int extraRandomHeight;

    public BigJungleTree( int baseHeight, int extraRandomHeight ) {
        this.baseHeight = baseHeight;
        this.extraRandomHeight = extraRandomHeight;
    }

    public void create(@NotNull Random random, @NotNull PopulationChunkManager manager, @NotNull Vector position ) {
        int height = this.getHeight( random );

        if ( this.ensureGrowable( manager, random, position, height ) ) {
            this.createCrown( manager, position.add( 0, height, 0 ), 2 );

            for ( int j = (int) position.getY() + height - 2 - random.nextInt( 4 ); j > position.getY() + height / 2; j -= 2 + random.nextInt( 4 ) ) {
                float f = random.nextFloat() * ( (float) Math.PI * 2F );
                int k = (int) ( position.getX() + ( 0.5F + Math.cos( f ) * 4.0F ) );
                int l = (int) ( position.getZ() + ( 0.5F + Math.sin( f ) * 4.0F ) );

                for ( int i1 = 0; i1 < 5; ++i1 ) {
                    k = (int) ( position.getX() + ( 1.5F + Math.cos( f ) * (float) i1 ) );
                    l = (int) ( position.getZ() + ( 1.5F + Math.sin( f ) * (float) i1 ) );
                    manager.setBlock( new Vector( k, j - 3 + i1 / 2, l ), BLOCK_LOG );
                }

                int j2 = 1 + random.nextInt( 2 );

                for ( int k1 = j - j2; k1 <= j; ++k1 ) {
                    int l1 = k1 - j;
                    this.growLeavesLayer( manager, new Vector( k, k1, l ), 1 - l1 );
                }
            }

            for ( int i2 = 0; i2 < height; ++i2 ) {
                Vector blockpos = position.add( 0, i2, 0 );

                if ( this.canPlace( manager.getBlock( blockpos ).getType() ) ) {
                    manager.setBlock( blockpos, BLOCK_LOG );

                    if ( i2 > 0 ) {
                        this.placeVine( manager, random, blockpos.west(), 8 );
                        this.placeVine( manager, random, blockpos.north(), 1 );
                    }
                }

                if ( i2 < height - 1 ) {

                    Vector blockpos1 = blockpos.east();
                    if ( this.canPlace( manager.getBlock( blockpos1 ).getType() ) ) {
                        manager.setBlock( blockpos1, BLOCK_LOG );

                        if ( i2 > 0 ) {
                            this.placeVine( manager, random, blockpos1.east(), 2 );
                            this.placeVine( manager, random, blockpos1.north(), 1 );
                        }
                    }

                    Vector blockpos2 = blockpos.south().east();
                    if ( this.canPlace( manager.getBlock( blockpos2 ).getType() ) ) {
                        manager.setBlock( blockpos2, BLOCK_LOG );

                        if ( i2 > 0 ) {
                            this.placeVine( manager, random, blockpos2.east(), 2 );
                            this.placeVine( manager, random, blockpos2.south(), 4 );
                        }
                    }

                    Vector blockpos3 = blockpos.south();
                    if ( this.canPlace( manager.getBlock( blockpos3 ).getType() ) ) {
                        manager.setBlock( blockpos3, BLOCK_LOG );

                        if ( i2 > 0 ) {
                            this.placeVine( manager, random, blockpos3.west(), 8 );
                            this.placeVine( manager, random, blockpos3.south(), 4 );
                        }
                    }
                }
            }
        }
    }

    public boolean ensureGrowable(@NotNull PopulationChunkManager manager, Random random, @NotNull Vector position, int height ) {
        return this.isSpaceAt( manager, position, height ) && this.ensureDirtsUnderneath( position, manager );
    }

    private boolean ensureDirtsUnderneath(@NotNull Vector pos, @NotNull PopulationChunkManager manager ) {
        Vector blockpos = pos.down();
        BlockType block = manager.getBlock( blockpos ).getType();

        if ( ( block == BlockType.GRASS || block == BlockType.DIRT ) && pos.getY() >= 2 ) {
            manager.setBlock( blockpos, BLOCK_DIRT );
            manager.setBlock( blockpos.east(), BLOCK_DIRT );
            manager.setBlock( blockpos.south(), BLOCK_DIRT );
            manager.setBlock( blockpos.south().east(), BLOCK_DIRT );
            return true;
        } else {
            return false;
        }
    }

    private boolean isSpaceAt(@NotNull PopulationChunkManager manager, @NotNull Vector leavesPos, int height ) {
        boolean flag = true;

        if ( leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= 256 ) {
            for ( int i = 0; i <= 1 + height; ++i ) {
                int j = 2;

                if ( i == 0 ) {
                    j = 1;
                } else if ( i >= 1 + height - 2 ) {
                    j = 2;
                }

                for ( int k = -j; k <= j && flag; ++k ) {
                    for ( int l = -j; l <= j && flag; ++l ) {
                        Vector blockPos = leavesPos.add( k, i, l );
                        if ( leavesPos.getY() + i < 0 || leavesPos.getY() + i >= 256 || !this.canPlace( manager.getBlock( blockPos ).getType() ) ) {
                            flag = false;
                        }
                    }
                }
            }

            return flag;
        } else {
            return false;
        }
    }

    public void createCrown(@NotNull PopulationChunkManager manager, @NotNull Vector position, int value ) {
        for ( int j = -2; j <= 0; ++j ) {
            this.growLeavesLayerStrict( manager, position.add( 0, j, 0 ), value + 1 - j );
        }
    }

    public boolean canPlace(@NotNull BlockType blockType ) {
        return blockType.equals( BlockType.AIR ) ||
                blockType.equals( BlockType.LEAVES ) ||
                blockType.equals( BlockType.GRASS ) ||
                blockType.equals( BlockType.DIRT ) ||
                blockType.equals( BlockType.LOG ) ||
                blockType.equals( BlockType.LOG2 ) ||
                blockType.equals( BlockType.SAPLING ) ||
                blockType.equals( BlockType.VINE );
    }

    public void growLeavesLayer(@NotNull PopulationChunkManager manager, @NotNull Vector position, int width ) {
        int i = width * width;

        for ( int j = -width; j <= width + 1; ++j ) {
            for ( int k = -width; k <= width + 1; ++k ) {
                int l = j - 1;
                int i1 = k - 1;

                if ( j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i ) {
                    Vector blockpos = position.add( j, 0, k );
                    BlockType id = manager.getBlock( blockpos ).getType();

                    if ( id == BlockType.AIR || id == BlockType.LEAVES ) {
                        manager.setBlock( blockpos, BLOCK_LEAVES );
                    }
                }
            }
        }
    }

    protected void growLeavesLayerStrict(@NotNull PopulationChunkManager manager, @NotNull Vector position, int width ) {
        int i = width * width;

        for ( int j = -width; j <= width + 1; ++j ) {
            for ( int k = -width; k <= width + 1; ++k ) {
                int l = j - 1;
                int i1 = k - 1;

                if ( j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i ) {
                    Vector blockpos = position.add( j, 0, k );
                    BlockType id = manager.getBlock( blockpos ).getType();

                    if ( id == BlockType.AIR || id == BlockType.LEAVES ) {
                        manager.setBlock( blockpos, BLOCK_LEAVES );
                    }
                }
            }
        }
    }

    public void placeVine(@NotNull PopulationChunkManager manager, @NotNull Random random, @NotNull Vector position, int meta ) {
        if ( random.nextInt( 3 ) > 0 && manager.getBlock( position ).getType().equals( BlockType.AIR ) ) {
            manager.setBlock( position, ( (BlockVine) BLOCK_VINE.clone() ).setVineDirection( meta ) );
        }
    }

    private int getHeight(@NotNull Random rand ) {
        int height = rand.nextInt( 3 ) + this.baseHeight;
        if ( this.extraRandomHeight > 1 ) {
            height += rand.nextInt( this.extraRandomHeight );
        }
        return height;
    }

}
