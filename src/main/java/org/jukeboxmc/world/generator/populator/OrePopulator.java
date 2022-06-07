package org.jukeboxmc.world.generator.populator;

import org.jukeboxmc.block.BlockStone;
import org.jukeboxmc.util.Utils;
import org.jukeboxmc.world.World;
import org.jukeboxmc.world.chunk.Chunk;
import org.jukeboxmc.world.generator.object.OreType;

import java.util.Random;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OrePopulator implements Populator {

    private final int replaceRuntimeId = new BlockStone().getRuntimeId();

    private final OreType[] oreTypes;

    public OrePopulator( OreType[] oreTypes ) {
        this.oreTypes = oreTypes;
    }

    @Override
    public void populate( Random random, World world, Chunk chunk ) {
        int sx = chunk.getX() << 4;
        int ex = sx + 15;
        int sz = chunk.getZ() << 4;
        int ez = sz + 15;
        for (OreType type : this.oreTypes) {
            for (int i = 0; i < type.clusterCount; i++) {
                int x = Utils.randomRange(random, sx, ex);
                int z = Utils.randomRange(random, sz, ez);
                int y = Utils.randomRange(random, type.minHeight, type.maxHeight);
                if (chunk.getBlock(x, y, z, 0).getRuntimeId() != this.replaceRuntimeId) {
                    continue;
                }
                type.spawn(chunk, random, x, y, z);
            }
        }
    }
}
