package jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleCrimsonSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrimsonDoubleSlab extends Item {

    public ItemCrimsonDoubleSlab() {
        super ( "minecraft:crimson_double_slab" );
    }

    @Override
    public BlockDoubleCrimsonSlab getBlock() {
        return new BlockDoubleCrimsonSlab();
    }
}
