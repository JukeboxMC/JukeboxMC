package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement43;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement43 extends Item {

    public ItemElement43() {
        super ( "minecraft:element_43" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement43();
    }
}
