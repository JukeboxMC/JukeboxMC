package jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleCutCopperSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleCutCopperSlab extends Item{

    public ItemDoubleCutCopperSlab() {
        super( "minecraft:double_cut_copper_slab" );
    }

    @Override
    public BlockDoubleCutCopperSlab getBlock() {
        return new BlockDoubleCutCopperSlab();
    }
}
