package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.NormalGenerator;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundGenerator {

    protected Block topMaterial;
    protected Block groundMaterial;

    private final Block BLOCK_AIR = Block.create( BlockType.AIR );
    private final Block BLOCK_STONE = Block.create( BlockType.STONE );
    private final Block BLOCK_GRAVEL = Block.create( BlockType.GRAVEL );
    private final Block BLOCK_SANDSTONE = Block.create( BlockType.SANDSTONE );

    public GroundGenerator() {
        this.topMaterial = Block.create( BlockType.GRASS );
        this.groundMaterial = Block.create( BlockType.DIRT );
    }

    public void generateTerrainColumn( Chunk chunk, Random random, int chunkX, int chunkZ, double surfaceNoise ) {
        int seaLevel = NormalGenerator.WATER_HEIGHT;

        Block topMaterial = this.topMaterial;
        Block groundMaterial = this.groundMaterial;

        int x = chunkX & 0xF;
        int z = chunkZ & 0xF;

        int surfaceHeight = Math.max( (int) ( surfaceNoise / 3.0D + 3.0D + random.nextDouble() * 0.25D ), 1 );
        int deep = -1;
        for ( int y = 255; y >= 0; y-- ) {
            BlockType blockType = chunk.getBlock( x, y, z, 0 ).getType();
            if ( blockType.equals( BlockType.AIR ) ) {
                deep = -1;
            } else if ( blockType.equals( BlockType.STONE ) ) {
                if ( deep == -1 ) {
                    if ( y >= seaLevel - 5 && y <= seaLevel ) {
                        topMaterial = this.topMaterial;
                        groundMaterial = this.groundMaterial;
                    }
                    deep = surfaceHeight;
                    if ( y >= seaLevel - 2 ) {
                        chunk.setBlock( x, y, z, 0, topMaterial );
                    } else if ( y < seaLevel - 8 - surfaceHeight ) {
                        topMaterial = BLOCK_AIR;
                        groundMaterial = BLOCK_STONE;
                        chunk.setBlock( x, y, z, 0, BLOCK_GRAVEL );
                    } else {
                        chunk.setBlock( x, y, z, 0, groundMaterial );
                    }
                } else if ( deep > 0 ) {
                    deep--;
                    chunk.setBlock( x, y, z, 0, groundMaterial );

                    if ( deep == 0 && groundMaterial.getType().equals( BlockType.SAND ) ) {
                        deep = random.nextInt( 4 ) + Math.max( 0, y - seaLevel - 1 );
                        groundMaterial = BLOCK_SANDSTONE;
                    }
                }
            }
            /*
             else if ( blockType == Block.STILL_WATER && y == seaLevel - 2 && BiomeClimate.isCold( biome, chunkX, y, chunkZ ) ) {
                chunk.setBlock( x, y, z, ICE );
            }
             */
        }
    }
}
