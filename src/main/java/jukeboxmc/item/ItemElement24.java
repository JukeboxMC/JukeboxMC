package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement24;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement24 extends Item {

    public ItemElement24() {
        super ( "minecraft:element_24" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement24();
    }
}
