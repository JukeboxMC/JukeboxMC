package jukeboxmc.item;

import org.jukeboxmc.block.BlockOxidizedDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOxidizedDoubleCutCopperSlab extends Item{

    public ItemOxidizedDoubleCutCopperSlab() {
        super( "minecraft:oxidized_double_cut_copper_slab" );
    }

    @Override
    public BlockOxidizedDoubleCutCopperSlab getBlock() {
        return new BlockOxidizedDoubleCutCopperSlab();
    }
}
