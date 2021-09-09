package jukeboxmc.item;

import org.jukeboxmc.block.BlockTintedGlass;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemTintedGlass extends Item{

    public ItemTintedGlass() {
        super( "minecraft:tinted_glass" );
    }

    @Override
    public BlockTintedGlass getBlock() {
        return new BlockTintedGlass();
    }
}
