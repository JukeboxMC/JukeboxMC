package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement75;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement75 extends Item {

    public ItemElement75() {
        super ( "minecraft:element_75" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement75();
    }
}
