package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;
import org.jukeboxmc.world.generator.NormalGenerator;

import java.util.List;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class DiskPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private final double probability;
    private final Block sourceBlock;
    private final int radiusMin;
    private final int radiusMax;
    private final int radiusY;
    private final List<BlockType> replaceBlocks;

    public DiskPopulator( double probability, Block sourceBlock, int radiusMin, int radiusMax, int radiusY, List<BlockType> replaceBlocks ) {
        this.probability = probability;
        this.sourceBlock = sourceBlock;
        this.radiusMin = radiusMin;
        this.radiusMax = radiusMax;
        this.radiusY = radiusY;
        this.replaceBlocks = replaceBlocks;
    }

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            if ( random.nextDouble() >= this.probability ) {
                return;
            }

            int sourceX = ( chunk.getX() << 4 ) + random.nextInt( 16 );
            int sourceZ = ( chunk.getZ() << 4 ) + random.nextInt( 16 );
            int sourceY = getHighestWorkableBlock( chunk, sourceX, sourceZ ) - 1;
            if ( sourceY < this.radiusY ) {
                return;
            }

            if ( !chunk.getBlock( sourceX, sourceY + 1, sourceZ, 0 ).getType().equals( BlockType.WATER ) ) {
                return;
            }

            int radius = random.nextInt( this.radiusMin, this.radiusMax );
            for ( int xx = sourceX - radius; xx <= sourceX + radius; xx++ ) {
                for ( int zz = sourceZ - radius; zz <= sourceZ + radius; zz++ ) {
                    if ( ( xx - sourceX ) * ( xx - sourceX ) + ( zz - sourceZ ) * ( zz - sourceZ ) <= radius * radius ) {
                        for ( int yy = sourceY - this.radiusY; yy <= sourceY + this.radiusY; yy++ ) {
                            for ( BlockType replaceBlockState : this.replaceBlocks ) {
                                if ( chunk.getBlock( xx, yy, zz, 0 ).getType().equals( replaceBlockState ) ) {
                                    chunk.setBlock( xx, yy, zz, 0, this.sourceBlock );
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getHighestWorkableBlock(@NotNull Chunk chunk, int x, int z ) {
        int y;
        for ( y = NormalGenerator.WATER_HEIGHT - 1; y >= 0; --y ) {
            Block block = chunk.getBlock( x, y, z, 0 );
            if ( block.getType() != BlockType.AIR &&
                    block.getType() != BlockType.WATER &&
                    block.getType() != BlockType.FLOWING_WATER &&
                    block.getType() != BlockType.ICE &&
                    block.getType() != BlockType.PACKED_ICE &&
                    block.getType() != BlockType.BLUE_ICE ) {
                break;
            }
        }
        return y == 0 ? -1 : ++y;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }
}
