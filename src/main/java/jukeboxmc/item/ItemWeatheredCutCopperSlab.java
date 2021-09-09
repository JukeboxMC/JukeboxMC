package jukeboxmc.item;

import org.jukeboxmc.block.BlockWeatheredCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeatheredCutCopperSlab extends Item{

    public ItemWeatheredCutCopperSlab() {
        super( "minecraft:weathered_cut_copper_slab" );
    }

    @Override
    public BlockWeatheredCutCopperSlab getBlock() {
        return new BlockWeatheredCutCopperSlab();
    }
}
