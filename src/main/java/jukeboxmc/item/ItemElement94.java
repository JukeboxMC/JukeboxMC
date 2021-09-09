package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement94;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement94 extends Item {

    public ItemElement94() {
        super ( "minecraft:element_94" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement94();
    }
}
