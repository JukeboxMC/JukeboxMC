package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement52;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement52 extends Item {

    public ItemElement52() {
        super ( "minecraft:element_52" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement52();
    }
}
