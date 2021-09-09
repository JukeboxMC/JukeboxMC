package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedExposedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedExposedCutCopperStairs extends Item{

    public ItemWaxedExposedCutCopperStairs() {
        super( "minecraft:waxed_exposed_cut_copper_stairs" );
    }

    @Override
    public BlockWaxedExposedCutCopperStairs getBlock() {
        return new BlockWaxedExposedCutCopperStairs();
    }
}
