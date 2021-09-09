package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement22;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement22 extends Item {

    public ItemElement22() {
        super ( "minecraft:element_22" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement22();
    }
}
