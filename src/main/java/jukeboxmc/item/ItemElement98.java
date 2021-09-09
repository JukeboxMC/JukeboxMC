package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement98;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement98 extends Item {

    public ItemElement98() {
        super ( "minecraft:element_98" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement98();
    }
}
