package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement45;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement45 extends Item {

    public ItemElement45() {
        super ( "minecraft:element_45" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement45();
    }
}
