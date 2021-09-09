package jukeboxmc.item;

import org.jukeboxmc.block.BlockPrismarineBricksStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPrismarineBricksStairs extends Item {

    public ItemPrismarineBricksStairs() {
        super ( "minecraft:prismarine_bricks_stairs" );
    }

    @Override
    public BlockPrismarineBricksStairs getBlock() {
        return new BlockPrismarineBricksStairs();
    }
}
