package jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickFence;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherBrickFence extends Item {

    public ItemNetherBrickFence() {
        super ( "minecraft:nether_brick_fence" );
    }

    @Override
    public BlockNetherBrickFence getBlock() {
        return new BlockNetherBrickFence();
    }
}
