package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement47;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement47 extends Item {

    public ItemElement47() {
        super ( "minecraft:element_47" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement47();
    }
}
