package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement118;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement118 extends Item {

    public ItemElement118() {
        super ( "minecraft:element_118" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement118();
    }
}
