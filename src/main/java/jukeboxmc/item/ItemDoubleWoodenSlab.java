package jukeboxmc.item;

import org.jukeboxmc.block.BlockDoubleWoodenSlab;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDoubleWoodenSlab extends Item {

    public ItemDoubleWoodenSlab() {
        super ( "minecraft:double_stone_slab" );
    }

    @Override
    public BlockDoubleWoodenSlab getBlock() {
        return new BlockDoubleWoodenSlab();
    }
}
