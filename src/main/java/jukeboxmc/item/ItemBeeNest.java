package jukeboxmc.item;

import org.jukeboxmc.block.BlockBeeNest;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeeNest extends Item {

    public ItemBeeNest() {
        super ( "minecraft:bee_nest" );
    }

    @Override
    public BlockBeeNest getBlock() {
        return new BlockBeeNest();
    }
}
