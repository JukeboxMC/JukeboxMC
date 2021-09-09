package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement2;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement2 extends Item {

    public ItemElement2() {
        super ( "minecraft:element_2" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement2();
    }
}
