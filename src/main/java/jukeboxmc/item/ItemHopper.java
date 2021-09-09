package jukeboxmc.item;

import org.jukeboxmc.block.BlockHopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHopper extends Item {

    public ItemHopper() {
        super ( "minecraft:hopper" );
    }

    @Override
    public BlockHopper getBlock() {
        return new BlockHopper();
    }
}
