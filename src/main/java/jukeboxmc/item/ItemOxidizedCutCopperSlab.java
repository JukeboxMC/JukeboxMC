package jukeboxmc.item;

import org.jukeboxmc.block.BlockOxidizedCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemOxidizedCutCopperSlab extends Item{

    public ItemOxidizedCutCopperSlab() {
        super( "minecraft:oxidized_cut_copper_slab" );
    }

    @Override
    public BlockOxidizedCutCopperSlab getBlock() {
        return new BlockOxidizedCutCopperSlab();
    }
}
