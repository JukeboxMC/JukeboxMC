package jukeboxmc.item;

import org.jukeboxmc.block.BlockNetherrack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemNetherrack extends Item {

    public ItemNetherrack() {
        super ( "minecraft:netherrack" );
    }

    @Override
    public BlockNetherrack getBlock() {
        return new BlockNetherrack();
    }
}
