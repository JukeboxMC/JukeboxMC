package org.jukeboxmc.world.generator;

import org.jukeboxmc.block.*;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.Dimension;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlatGenerator extends Generator {

    private final Block blockStone;
    private final Block blockGrass;
    private final Block blockDirt;
    private final Block blockBedrock;
    private final Block blockRedstone;
    private final Block blockLapis;
    private final Block blockEmerald;

    public FlatGenerator( World world, Dimension dimension ) {
        super( world, dimension );

        this.blockStone = new BlockStone();
        this.blockGrass = new BlockGrass();
        this.blockDirt = new BlockDirt();
        this.blockBedrock = new BlockBedrock();
        this.blockRedstone = new BlockRedstoneBlock();
        this.blockLapis = new BlockLapisBlock();
        this.blockEmerald = new BlockEmeraldBlock();
    }

    @Override
    public void generate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );
        for ( int blockX = 0; blockX < 16; blockX++ ) {
            for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                for ( int blockY = 0; blockY < 16; blockY++ ) {
                    chunk.setBiome( blockX, blockY, blockZ, Biome.PLAINS );
                }

                chunk.setBlock( blockX, 0, blockZ, 0, this.blockStone );
                chunk.setBlock( blockX, 1, blockZ, 0, this.blockStone );
                chunk.setBlock( blockX, 2, blockZ, 0, this.blockStone );
                chunk.setBlock( blockX, 3, blockZ, 0, this.blockStone );
            }
        }
    }

    @Override
    public void populate( int chunkX, int chunkZ ) {
        Chunk chunk = this.getChunk( chunkX, chunkZ );
        for ( int blockX = 0; blockX < 16; blockX++ ) {
            for ( int blockZ = 0; blockZ < 16; blockZ++ ) {
                chunk.setBlock( blockX, 0, blockZ, 0, this.blockBedrock );
                chunk.setBlock( blockX, 1, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 2, blockZ, 0, this.blockDirt );
                chunk.setBlock( blockX, 3, blockZ, 0, this.blockGrass );
            }
        }

        Chunk chunkLeft = this.getChunk( chunkX + 1, chunkZ );

        chunkLeft.setBlock( 0, 8, 0, 0, this.blockBedrock );

        Chunk chunkRight = this.getChunk( chunkX - 1, chunkZ );

        chunkRight.setBlock( 0, 9, 0, 0, this.blockRedstone );

        Chunk chunkUp = this.getChunk( chunkX, chunkZ + 1 );

        chunkUp.setBlock( 0, 10, 0, 0, this.blockLapis );

        Chunk chunkDown = this.getChunk( chunkX, chunkZ - 1 );

        chunkDown.setBlock( 0, 11, 0, 0, this.blockEmerald );
    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 0, 5, 0 );
    }
}
