package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement54;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement54 extends Item {

    public ItemElement54() {
        super ( "minecraft:element_54" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement54();
    }
}
