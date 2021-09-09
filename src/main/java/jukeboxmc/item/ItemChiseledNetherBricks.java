package jukeboxmc.item;

import org.jukeboxmc.block.BlockChiseledNetherBricks;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemChiseledNetherBricks extends Item {

    public ItemChiseledNetherBricks() {
        super( "minecraft:chiseled_nether_bricks" );
    }

    @Override
    public BlockChiseledNetherBricks getBlock() {
        return new BlockChiseledNetherBricks();
    }
}
