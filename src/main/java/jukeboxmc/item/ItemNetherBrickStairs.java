package jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherBrickStairs extends Item {

    public ItemNetherBrickStairs() {
        super ( "minecraft:nether_brick_stairs" );
    }

    @Override
    public BlockNetherBrickStairs getBlock() {
        return new BlockNetherBrickStairs();
    }
}
