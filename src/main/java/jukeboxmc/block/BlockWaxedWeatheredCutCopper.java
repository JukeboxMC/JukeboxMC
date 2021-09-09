package jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedWeatheredCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedWeatheredCutCopper extends Block{

    public BlockWaxedWeatheredCutCopper() {
        super( "minecraft:waxed_weathered_cut_copper" );
    }

    @Override
    public ItemWaxedWeatheredCutCopper toItem() {
        return new ItemWaxedWeatheredCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_WEATHERED_CUT_COPPER;
    }
}
