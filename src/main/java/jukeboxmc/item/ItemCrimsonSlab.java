package jukeboxmc.item;

import org.jukeboxmc.block.BlockCrimsonSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonSlab extends Item {

    public ItemCrimsonSlab() {
        super ( "minecraft:crimson_slab" );
    }

    @Override
    public BlockCrimsonSlab getBlock() {
        return new BlockCrimsonSlab();
    }
}
