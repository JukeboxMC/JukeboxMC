package jukeboxmc.item;

import org.jukeboxmc.block.BlockBamboo;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBamboo extends Item {

    public ItemBamboo() {
        super ( "minecraft:bamboo" );
    }

    @Override
    public BlockBamboo getBlock() {
        return new BlockBamboo();
    }
}
