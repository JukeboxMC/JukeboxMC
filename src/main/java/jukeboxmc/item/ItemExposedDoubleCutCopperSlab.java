package jukeboxmc.item;

import org.jukeboxmc.block.BlockExposedDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemExposedDoubleCutCopperSlab extends Item{

    public ItemExposedDoubleCutCopperSlab() {
        super( "minecraft:exposed_double_cut_copper_slab" );
    }

    @Override
    public BlockExposedDoubleCutCopperSlab getBlock() {
        return new BlockExposedDoubleCutCopperSlab();
    }
}
