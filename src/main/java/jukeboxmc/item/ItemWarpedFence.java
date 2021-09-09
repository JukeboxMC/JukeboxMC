package jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedFence;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedFence extends Item {

    public ItemWarpedFence() {
        super ( "minecraft:warped_fence" );
    }

    @Override
    public BlockWarpedFence getBlock() {
        return new BlockWarpedFence();
    }
}
