package jukeboxmc.item;

import org.jukeboxmc.block.BlockPrismarineStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarineStairs extends Item {

    public ItemPrismarineStairs() {
        super ( "minecraft:prismarine_stairs" );
    }

    @Override
    public BlockPrismarineStairs getBlock() {
        return new BlockPrismarineStairs();
    }
}
