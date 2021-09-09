package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedCutCopperSlab extends Item {

    public ItemWaxedCutCopperSlab() {
        super( "minecraft:waxed_cut_copper_slab" );
    }

    @Override
    public BlockWaxedCutCopperSlab getBlock() {
        return new BlockWaxedCutCopperSlab();
    }
}
