package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockHangingRoots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHangingRoots extends Item {

    public ItemHangingRoots() {
        super( "minecraft:hanging_roots" );
    }

    @Override
    public Block getBlock() {
        return new BlockHangingRoots();
    }
}
