package jukeboxmc.item;

import org.jukeboxmc.block.BlockCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCutCopperSlab extends Item{

    public ItemCutCopperSlab() {
        super( "minecraft:cut_copper_slab" );
    }

    @Override
    public BlockCutCopperSlab getBlock() {
        return new BlockCutCopperSlab();
    }
}
