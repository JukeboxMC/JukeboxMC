package org.jukeboxmc.world.generator.biome.generation;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockDirt;
import org.jukeboxmc.block.data.DirtType;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.biome.GroundGenerator;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundGeneratorPatchDirtAndStone extends GroundGenerator {

    @Override
    public void generateTerrainColumn( Chunk chunk, Random random, int chunkX, int chunkZ, double surfaceNoise ) {
        if ( surfaceNoise > 1.75D ) {
            this.topMaterial = Block.create( BlockType.STONE );
            this.groundMaterial = Block.create( BlockType.STONE );
        } else if ( surfaceNoise > -0.5D ) {
            this.topMaterial = Block.<BlockDirt>create( BlockType.DIRT ).setDirtType( DirtType.COARSE );
            this.groundMaterial = Block.create( BlockType.STONE );
        } else {
            this.topMaterial = Block.create( BlockType.GRASS );
            this.groundMaterial = Block.create( BlockType.DIRT );
        }
        super.generateTerrainColumn( chunk, random, chunkX, chunkZ, surfaceNoise );
    }
}
