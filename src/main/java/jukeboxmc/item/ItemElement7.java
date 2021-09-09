package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement7;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement7 extends Item {

    public ItemElement7() {
        super ( "minecraft:element_7" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement7();
    }
}
