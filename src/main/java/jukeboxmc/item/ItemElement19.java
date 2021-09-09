package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement19;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement19 extends Item {

    public ItemElement19() {
        super ( "minecraft:element_19" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement19();
    }
}
