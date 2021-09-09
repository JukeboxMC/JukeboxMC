package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement58;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement58 extends Item {

    public ItemElement58() {
        super ( "minecraft:element_58" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement58();
    }
}
