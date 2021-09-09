package jukeboxmc.item;

import org.jukeboxmc.block.BlockSugarCane;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSugarCane extends Item {

    public ItemSugarCane() {
        super ( "minecraft:sugar_cane" );
    }

    @Override
    public BlockSugarCane getBlock() {
        return new BlockSugarCane();
    }
}
