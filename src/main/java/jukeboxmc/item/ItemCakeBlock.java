package jukeboxmc.item;

import org.jukeboxmc.block.BlockCake;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCakeBlock extends Item {

    public ItemCakeBlock() {
        super ( "minecraft:item.cake" );
    }

    @Override
    public BlockCake getBlock() {
        return new BlockCake();
    }
}
