package org.jukeboxmc.world.generator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlatGenerator extends Generator {

    private final Block blockGrass;
    private final Block blockDirt;
    private final Block blockBedrock;

    public FlatGenerator( World world ) {
        this.blockGrass = Block.create( BlockType.GRASS );
        this.blockDirt = Block.create( BlockType.DIRT );
        this.blockBedrock = Block.create( BlockType.BEDROCK );
    }

    @Override
    public void generate( Chunk chunk, int chunkX, int chunkZ ) {
        for ( int blockX = 0; blockX < 16; blockX++ ) {
            for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                for ( int blockY = 0; blockY < 16; blockY++ ) {
                    chunk.setBiome( blockX, blockY, blockZ, Biome.PLAINS );
                }

                chunk.setBlock( blockX, 0, blockZ, 0, this.blockBedrock );
                chunk.setBlock( blockX, 1, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 2, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 3, blockZ, 0, this.blockDirt );
            }
        }
    }

    @Override
    public void populate( PopulationChunkManager manager, int chunkX, int chunkZ ) {

    }

    @Override
    public void finish( PopulationChunkManager manager, int chunkX, int chunkZ ) {

    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0.5f, 5, 0.5f );
    }
}
