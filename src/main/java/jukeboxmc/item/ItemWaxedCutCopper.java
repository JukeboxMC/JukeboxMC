package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedCutCopper extends Item{

    public ItemWaxedCutCopper() {
        super( "minecraft:waxed_cut_copper" );
    }

    @Override
    public BlockWaxedCopper getBlock() {
        return new BlockWaxedCopper();
    }
}
