package org.jukeboxmc.world.generator.biome.generation;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.generator.biome.GroundGenerator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundGeneratorRocky extends GroundGenerator {

    public GroundGeneratorRocky() {
        this.topMaterial = Block.create( BlockType.STONE );
        this.groundMaterial = Block.create( BlockType.STONE );
    }
}


