package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaxedCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaxedCopper extends Item{

    public ItemWaxedCopper() {
        super( "minecraft:waxed_copper" );
    }

    @Override
    public BlockWaxedCopper getBlock() {
        return new BlockWaxedCopper();
    }
}
