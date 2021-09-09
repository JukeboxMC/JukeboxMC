package jukeboxmc.item;

import org.jukeboxmc.block.BlockOxidizedCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOxidizedCutCopperStairs extends Item{

    public ItemOxidizedCutCopperStairs() {
        super( "minecraft:oxidized_cut_copper_stairs" );
    }

    @Override
    public BlockOxidizedCutCopperStairs getBlock() {
        return new BlockOxidizedCutCopperStairs();
    }
}
