package jukeboxmc.item;

import org.jukeboxmc.block.BlockWarpedSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWarpedSlab extends Item {

    public ItemWarpedSlab() {
        super ( "minecraft:warped_slab" );
    }

    @Override
    public BlockWarpedSlab getBlock() {
        return new BlockWarpedSlab();
    }
}
