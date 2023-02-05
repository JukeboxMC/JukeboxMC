package org.jukeboxmc.world.generator.biome.generation;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockType;
import org.jukeboxmc.world.generator.biome.GroundGenerator;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class GroundGeneratorSandy extends GroundGenerator {

    public GroundGeneratorSandy() {
        this.topMaterial = Block.create( BlockType.SAND );
        this.groundMaterial = Block.create( BlockType.SAND );
    }
}
