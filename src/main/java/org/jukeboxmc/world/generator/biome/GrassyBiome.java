package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.block.BlockGrass;

import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GrassyBiome extends BiomeGeneration {

    @Override
    public List<Block> getGround() {
        return Arrays.asList(
                new BlockGrass(),
                new BlockDirt(),
                new BlockDirt(),
                new BlockDirt(),
                new BlockDirt()
        );
    }
}
