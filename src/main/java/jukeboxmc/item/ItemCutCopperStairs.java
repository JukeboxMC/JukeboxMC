package jukeboxmc.item;

import org.jukeboxmc.block.BlockCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCutCopperStairs extends Item{

    public ItemCutCopperStairs() {
        super( "minecraft:cut_copper_stairs" );
    }

    @Override
    public BlockCutCopperStairs getBlock() {
        return new BlockCutCopperStairs();
    }
}
