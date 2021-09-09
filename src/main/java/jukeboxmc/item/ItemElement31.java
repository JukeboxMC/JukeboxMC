package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement31;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement31 extends Item {

    public ItemElement31() {
        super ( "minecraft:element_31" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement31();
    }
}
