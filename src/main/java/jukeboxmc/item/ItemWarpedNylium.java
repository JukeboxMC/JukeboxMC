package jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedNylium;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedNylium extends Item {

    public ItemWarpedNylium() {
        super ( "minecraft:warped_nylium" );
    }

    @Override
    public BlockWarpedNylium getBlock() {
        return new BlockWarpedNylium();
    }
}
