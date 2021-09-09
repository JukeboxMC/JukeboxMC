package jukeboxmc.item;

import org.jukeboxmc.block.BlockPackedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemPackedIce extends Item {

    public ItemPackedIce() {
        super ( "minecraft:packed_ice" );
    }

    @Override
    public BlockPackedIce getBlock() {
        return new BlockPackedIce();
    }
}
