package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedWeatheredCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedWeatheredCutCopperSlab extends Item{

    public ItemWaxedWeatheredCutCopperSlab() {
        super( "minecraft:waxed_weathered_cut_copper_slab" );
    }

    @Override
    public BlockWaxedWeatheredCutCopperSlab getBlock() {
        return new BlockWaxedWeatheredCutCopperSlab();
    }
}
