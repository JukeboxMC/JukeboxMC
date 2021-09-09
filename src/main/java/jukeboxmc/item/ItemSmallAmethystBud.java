package jukeboxmc.item;

import org.jukeboxmc.block.BlockSmallAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSmallAmethystBud extends Item{

    public ItemSmallAmethystBud() {
        super( "minecraft:small_amethyst_bud" );
    }

    @Override
    public BlockSmallAmethystBud getBlock() {
        return new BlockSmallAmethystBud();
    }
}
