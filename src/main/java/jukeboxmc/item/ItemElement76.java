package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement76;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement76 extends Item {

    public ItemElement76() {
        super ( "minecraft:element_76" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement76();
    }
}
