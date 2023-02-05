package org.jukeboxmc.world.generator.biome.generation;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.biome.GroundGenerator;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundGeneratorPatchGravel extends GroundGenerator {

    @Override
    public void generateTerrainColumn( Chunk chunk, Random random, int chunkX, int chunkZ, double surfaceNoise ) {
        if ( surfaceNoise < -1.0D || surfaceNoise > 2.0D ) {
            this.topMaterial = Block.create( BlockType.GRAVEL );
            this.groundMaterial = Block.create( BlockType.GRAVEL );
        } else {
            this.topMaterial = Block.create( BlockType.GRASS );
            this.groundMaterial = Block.create( BlockType.DIRT );
        }
        super.generateTerrainColumn( chunk, random, chunkX, chunkZ, surfaceNoise );
    }
}
