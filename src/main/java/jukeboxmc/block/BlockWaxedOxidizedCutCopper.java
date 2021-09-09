package jukeboxmc.block;

import org.jukeboxmc.item.ItemWaxedOxidizedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWaxedOxidizedCutCopper extends Block{

    public BlockWaxedOxidizedCutCopper() {
        super( "minecraft:waxed_oxidized_cut_copper" );
    }

    @Override
    public ItemWaxedOxidizedCutCopper toItem() {
        return new ItemWaxedOxidizedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WAXED_OXIDIZED_CUT_COPPER;
    }
}
