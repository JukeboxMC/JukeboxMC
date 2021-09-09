package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedOxidizedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedOxidizedCutCopper extends Item{

    public ItemWaxedOxidizedCutCopper() {
        super( "minecraft:waxed_oxidized_cut_copper" );
    }

    @Override
    public BlockWaxedOxidizedCutCopper getBlock() {
        return new BlockWaxedOxidizedCutCopper();
    }
}
