package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement20;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement20 extends Item {

    public ItemElement20() {
        super ( "minecraft:element_20" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement20();
    }
}
