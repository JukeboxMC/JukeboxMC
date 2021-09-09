package jukeboxmc.item;

import org.jukeboxmc.block.BlockBrownMushroom;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBrownMushroom extends Item {

    public ItemBrownMushroom() {
        super ( "minecraft:brown_mushroom" );
    }

    @Override
    public BlockBrownMushroom getBlock() {
        return new BlockBrownMushroom();
    }
}
