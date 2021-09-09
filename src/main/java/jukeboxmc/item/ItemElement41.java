package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement41;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement41 extends Item {

    public ItemElement41() {
        super ( "minecraft:element_41" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement41();
    }
}
