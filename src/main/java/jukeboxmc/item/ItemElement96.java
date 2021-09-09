package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement69;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement96 extends Item {

    public ItemElement96() {
        super ( "minecraft:element_96" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement69();
    }
}
