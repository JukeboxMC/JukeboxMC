package jukeboxmc.item;

import org.jukeboxmc.block.Block;
import org.jukeboxmc.block.BlockElement73;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemElement73 extends Item {

    public ItemElement73() {
        super ( "minecraft:element_73" );
    }

    @Override
    public Block getBlock() {
        return new BlockElement73();
    }
}
