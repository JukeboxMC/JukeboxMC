package jukeboxmc.item;

import org.jukeboxmc.block.BlockOakStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOakStairs extends Item {

    public ItemOakStairs() {
        super ( "minecraft:oak_stairs" );
    }

    @Override
    public BlockOakStairs getBlock() {
        return new BlockOakStairs();
    }
}
