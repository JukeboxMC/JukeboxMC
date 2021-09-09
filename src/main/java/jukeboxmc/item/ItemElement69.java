package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement69;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement69 extends Item {

    public ItemElement69() {
        super ( "minecraft:element_69" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement69();
    }
}
