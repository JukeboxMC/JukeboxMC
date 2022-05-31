package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockGravel;

import java.util.Arrays;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OceanBiome extends BiomeGeneration {

    @Override
    public List<Block> getGround() {
        return Arrays.asList(
          new BlockGravel(),
          new BlockGravel(),
          new BlockGravel()
        );
    }

    @Override
    public Block getDefaultGround() {
        return new BlockGravel();
    }
}
