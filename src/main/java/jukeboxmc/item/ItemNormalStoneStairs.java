package jukeboxmc.item;

import org.jukeboxmc.block.BlockNormalStoneStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNormalStoneStairs extends Item {

    public ItemNormalStoneStairs() {
        super ( "minecraft:normal_stone_stairs" );
    }

    @Override
    public BlockNormalStoneStairs getBlock() {
        return new BlockNormalStoneStairs();
    }
}
