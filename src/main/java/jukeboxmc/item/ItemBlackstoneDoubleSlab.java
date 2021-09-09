package jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneDoubleSlab extends Item {

    public ItemBlackstoneDoubleSlab() {
        super ( "minecraft:blackstone_double_slab" );
    }

    @Override
    public BlockDoubleBlackstoneSlab getBlock() {
        return new BlockDoubleBlackstoneSlab();
    }
}
