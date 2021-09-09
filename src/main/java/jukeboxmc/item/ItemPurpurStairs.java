package jukeboxmc.item;

import org.jukeboxmc.block.BlockPurpurStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPurpurStairs extends Item {

    public ItemPurpurStairs() {
        super ( "minecraft:purpur_stairs" );
    }

    @Override
    public BlockPurpurStairs getBlock() {
        return new BlockPurpurStairs();
    }
}
