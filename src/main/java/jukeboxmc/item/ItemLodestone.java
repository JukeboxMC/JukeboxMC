package jukeboxmc.item;

import org.jukeboxmc.block.BlockLodestone;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLodestone extends Item {

    public ItemLodestone() {
        super ( "minecraft:lodestone" );
    }

    @Override
    public BlockLodestone getBlock() {
        return new BlockLodestone();
    }
}
