package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedExposedCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedExposedCutCopperSlab extends Item{

    public ItemWaxedExposedCutCopperSlab() {
        super( "minecraft:waxed_exposed_cut_copper_slab" );
    }

    @Override
    public BlockWaxedExposedCutCopperSlab getBlock() {
        return new BlockWaxedExposedCutCopperSlab();
    }
}
