package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedOxidizedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedOxidizedCutCopperStairs extends Item{

    public ItemWaxedOxidizedCutCopperStairs() {
        super( "minecraft:waxed_oxidized_cut_copper_stairs" );
    }

    @Override
    public BlockWaxedOxidizedCutCopperStairs getBlock() {
        return new BlockWaxedOxidizedCutCopperStairs();
    }
}
