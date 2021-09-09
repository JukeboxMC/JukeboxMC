package jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonRoots extends Item {

    public ItemCrimsonRoots() {
        super ( "minecraft:crimson_roots" );
    }

    @Override
    public BlockCrimsonRoots getBlock() {
        return new BlockCrimsonRoots();
    }
}
