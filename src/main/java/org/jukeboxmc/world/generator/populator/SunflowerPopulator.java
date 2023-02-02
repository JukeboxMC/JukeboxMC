package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockDoublePlant;
import org.jukeboxmc.block.behavior.BlockRedFlower;
import org.jukeboxmc.block.data.FlowerType;
import org.jukeboxmc.block.data.PlantType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SunflowerPopulator extends Populator{

    private int randomAmount;
    private int baseAmount;

    private final Block BLOCK_SUNFLOWER = Block.create( BlockType.DOUBLE_PLANT );

    @Override
    public void populate( Random random, World world, PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( this.canFlowerStay( chunk, x, y, z ) ) {
                chunk.setBlock( x, y, z, 0, ( (BlockDoublePlant) BLOCK_SUNFLOWER.clone() ).setPlantType( PlantType.SUNFLOWER ).setUpperBlock( false ) );
                chunk.setBlock( x, y + 1, z, 0, ( (BlockDoublePlant) BLOCK_SUNFLOWER.clone() ).setPlantType( PlantType.SUNFLOWER ).setUpperBlock( true ) );
            }
        }
    }

    private boolean canFlowerStay( Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        boolean val1 = block.getType().equals( BlockType.AIR ) || block.getType().equals( BlockType.TALLGRASS );
        boolean val2 = this.blockBelow( chunk, x, y, z, BlockType.GRASS );
        return val1 && val2;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }
}
