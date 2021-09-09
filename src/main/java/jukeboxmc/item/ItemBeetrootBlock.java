package jukeboxmc.item;

import org.jukeboxmc.block.BlockBeetroot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBeetrootBlock extends Item {

    public ItemBeetrootBlock() {
        super( "minecraft:item.beetroot" );
    }

    @Override
    public BlockBeetroot getBlock() {
        return new BlockBeetroot();
    }
}
