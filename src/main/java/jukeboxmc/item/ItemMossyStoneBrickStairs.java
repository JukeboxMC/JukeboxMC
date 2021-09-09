package jukeboxmc.item;

import org.jukeboxmc.block.BlockMossyStoneBrickStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMossyStoneBrickStairs extends Item {

    public ItemMossyStoneBrickStairs() {
        super ( "minecraft:mossy_stone_brick_stairs" );
    }

    @Override
    public BlockMossyStoneBrickStairs getBlock() {
        return new BlockMossyStoneBrickStairs();
    }
}
