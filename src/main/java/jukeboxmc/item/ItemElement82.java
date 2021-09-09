package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement82;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement82 extends Item {

    public ItemElement82() {
        super ( "minecraft:element_82" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement82();
    }
}
