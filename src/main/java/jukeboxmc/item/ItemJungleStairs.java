package jukeboxmc.item;

import org.jukeboxmc.block.BlockJungleStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJungleStairs extends Item {

    public ItemJungleStairs() {
        super ( "minecraft:jungle_stairs" );
    }

    @Override
    public BlockJungleStairs getBlock() {
        return new BlockJungleStairs();
    }
}
