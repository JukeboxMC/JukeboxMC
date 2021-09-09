package jukeboxmc.item;

import org.jukeboxmc.block.BlockLargeAmethystBud;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLargeAmethystBud extends Item{

    public ItemLargeAmethystBud() {
        super( "minecraft:large_amethyst_bud" );
    }

    @Override
    public BlockLargeAmethystBud getBlock() {
        return new BlockLargeAmethystBud();
    }
}
