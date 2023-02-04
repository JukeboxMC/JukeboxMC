package org.jukeboxmc.world.generator.object;

import org.jukeboxmc.block.Block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class OreType {

    private final Block block;
    private final int clusterCount;
    private final int clusterSize;
    private final int minHeight;
    private final int maxHeight;

    public OreType( Block block, int clusterCount, int clusterSize, int minHeight, int maxHeight ) {
        this.block = block;
        this.clusterCount = clusterCount;
        this.clusterSize = clusterSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getClusterCount() {
        return this.clusterCount;
    }

    public int getClusterSize() {
        return this.clusterSize;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }
}
