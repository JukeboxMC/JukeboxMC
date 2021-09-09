package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement9;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement9 extends Item {

    public ItemElement9() {
        super ( "minecraft:element_9" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement9();
    }
}
