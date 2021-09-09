package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement81;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement81 extends Item {

    public ItemElement81() {
        super ( "minecraft:element_81" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement81();
    }
}
