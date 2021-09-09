package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement110;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement110 extends Item{

    public ItemElement110() {
        super ( "minecraft:element_110" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement110();
    }
}
