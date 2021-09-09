package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement50;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement50 extends Item {

    public ItemElement50() {
        super ( "minecraft:element_50" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement50();
    }
}
