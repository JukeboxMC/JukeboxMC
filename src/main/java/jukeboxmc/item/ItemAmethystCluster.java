package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockAmethystCluster;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemAmethystCluster extends Item{

    public ItemAmethystCluster() {
        super( "minecraft:amethyst_cluster" );
    }

    @Override
    public Block getBlock() {
        return new BlockAmethystCluster();
    }
}
