package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement80;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement80 extends Item {

    public ItemElement80() {
        super ( "minecraft:element_80" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement80();
    }
}
