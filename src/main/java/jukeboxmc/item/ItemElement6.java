package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement6;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement6 extends Item {

    public ItemElement6() {
        super ( "minecraft:element_6" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement6();
    }
}
