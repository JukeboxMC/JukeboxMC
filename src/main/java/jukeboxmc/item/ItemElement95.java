package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement95;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement95 extends Item {

    public ItemElement95() {
        super ( "minecraft:element_95" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement95();
    }
}
