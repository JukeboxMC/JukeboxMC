package jukeboxmc.item;

import org.jukeboxmc.block.BlockRedMushroom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemRedMushroom extends Item {

    public ItemRedMushroom() {
        super ( "minecraft:red_mushroom" );
    }

    @Override
    public BlockRedMushroom getBlock() {
        return new BlockRedMushroom();
    }
}
