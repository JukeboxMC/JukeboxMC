package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface Populator {

    void populate( Random random, World world, Chunk chunk );

}
