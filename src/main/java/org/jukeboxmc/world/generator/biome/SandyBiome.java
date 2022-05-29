package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockSand;
import org.jukeboxmc.block.BlockSandstone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class SandyBiome extends BiomeGeneration {

    @Override
    public List<Block> getGround() {
        List<Block> blocks = new ArrayList<>();
        Random random = new Random();
        blocks.add( new BlockSand() );
        blocks.add( new BlockSand() );
        blocks.add( new BlockSand() );

        for ( int i = 0; i < random.nextInt(2); i++) {
            blocks.add( new BlockSandstone() );
        }
        return blocks;
    }

    @Override
    public Block getDefaultGround() {
        return new BlockSand();
    }
}