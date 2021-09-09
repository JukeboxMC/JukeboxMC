package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedDoubleCutCopperSlab extends Item{

    public ItemWaxedDoubleCutCopperSlab() {
        super( "minecraft:waxed_double_cut_copper_slab" );
    }

    @Override
    public BlockWaxedDoubleCutCopperSlab getBlock() {
        return new BlockWaxedDoubleCutCopperSlab();
    }
}
