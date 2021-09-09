package jukeboxmc.item;

import org.jukeboxmc.block.BlockCobbledDeepslateStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobbledDeepslateStairs extends Item{

    public ItemCobbledDeepslateStairs() {
        super( "minecraft:cobbled_deepslate_stairs" );
    }

    @Override
    public BlockCobbledDeepslateStairs getBlock() {
        return new BlockCobbledDeepslateStairs();
    }
}
