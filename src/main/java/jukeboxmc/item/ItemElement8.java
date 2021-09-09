package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement8;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement8 extends Item {

    public ItemElement8() {
        super ( "minecraft:element_8" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement8();
    }
}
