package org.jukeboxmc.world.generator.biome;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockDirt;
import org.jukeboxmc.world.generator.populator.Populator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BiomeGeneration {

    public List<Block> getGround() {
        return new ArrayList<>();
    }

    public List<Populator> getPopulators() {
        return new ArrayList<>();
    }

    public Block getDefaultGround() {
        return new BlockDirt();
    }
}
