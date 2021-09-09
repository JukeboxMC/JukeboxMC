package jukeboxmc.item;

import org.jukeboxmc.block.BlockWheat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWheatSeeds extends Item {

    public ItemWheatSeeds() {
        super ( "minecraft:wheat_seeds" );
    }

    @Override
    public BlockWheat getBlock() {
        return new BlockWheat();
    }
}
