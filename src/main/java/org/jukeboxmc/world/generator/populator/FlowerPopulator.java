package org.jukeboxmc.world.generator.populator;

import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.block.behavior.BlockRedFlower;
import org.jukeboxmc.block.data.FlowerType;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.chunk.manager.PopulationChunkManager;

import java.util.List;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class FlowerPopulator extends Populator {

    private int randomAmount;
    private int baseAmount;

    private List<FlowerType> flowerTypes;

    private final Block BLOCK_FLOWER = Block.create( BlockType.RED_FLOWER );

    @Override
    public void populate(@NotNull Random random, World world, @NotNull PopulationChunkManager chunkManager, int chunkX, int chunkZ ) {
        int amount = random.nextInt( this.randomAmount + 1 ) + this.baseAmount;
        Chunk chunk = chunkManager.getChunk( chunkX, chunkZ );
        for ( int i = 0; i < amount; ++i ) {
            int x = random.nextInt( 16 );
            int z = random.nextInt( 16 );
            int y = this.getHighestWorkableBlock( chunk, x, z );

            if ( this.canFlowerStay( chunk, x, y, z ) ) {
                FlowerType flowerType = this.flowerTypes.get( random.nextInt( this.flowerTypes.size() ) );
                chunk.setBlock( x, y, z, 0, ( (BlockRedFlower) BLOCK_FLOWER.clone() ).setFlowerType( flowerType ) );
            }
        }
    }

    private boolean canFlowerStay(@NotNull Chunk chunk, int x, int y, int z ) {
        Block block = chunk.getBlock( x, y, z, 0 );
        boolean val1 = block.getType().equals( BlockType.AIR ) || block.getType().equals( BlockType.SNOW_LAYER ) || block.getType().equals( BlockType.TALLGRASS );
        boolean val2 = this.blockBelow( chunk, x, y, z, BlockType.GRASS );
        return val1 && val2;
    }

    public void setRandomAmount( int randomAmount ) {
        this.randomAmount = randomAmount;
    }

    public void setBaseAmount( int baseAmount ) {
        this.baseAmount = baseAmount;
    }

    public void setFlowerTypes( List<FlowerType> flowerTypes ) {
        this.flowerTypes = flowerTypes;
    }
}
