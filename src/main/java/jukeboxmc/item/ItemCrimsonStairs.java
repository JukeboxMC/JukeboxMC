package jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonStairs extends Item {

    public ItemCrimsonStairs() {
        super ( "minecraft:crimson_stairs" );
    }

    @Override
    public BlockCrimsonStairs getBlock() {
        return new BlockCrimsonStairs();
    }
}
