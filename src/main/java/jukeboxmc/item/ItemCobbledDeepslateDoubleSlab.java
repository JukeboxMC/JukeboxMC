package jukeboxmc.item;

import org.jukeboxmc.block.BlockCobbledDeepslateSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCobbledDeepslateDoubleSlab extends Item{

    public ItemCobbledDeepslateDoubleSlab() {
        super( "minecraft:cobbled_deepslate_double_slab" );
    }

    @Override
    public BlockCobbledDeepslateSlab getBlock() {
        return new BlockCobbledDeepslateSlab();
    }
}
