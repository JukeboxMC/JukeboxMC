package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement4;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement4 extends Item {

    public ItemElement4() {
        super ( "minecraft:element_4" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement4();
    }
}
