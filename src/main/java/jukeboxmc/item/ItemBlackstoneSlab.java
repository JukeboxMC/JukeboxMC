package jukeboxmc.item;

import org.jukeboxmc.block.BlockBlackstoneSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlackstoneSlab extends Item {

    public ItemBlackstoneSlab() {
        super ( "minecraft:blackstone_slab" );
    }

    @Override
    public BlockBlackstoneSlab getBlock() {
        return new BlockBlackstoneSlab();
    }
}
