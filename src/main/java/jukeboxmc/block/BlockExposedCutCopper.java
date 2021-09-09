package jukeboxmc.block;

import org.jukeboxmc.item.ItemExposedCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockExposedCutCopper extends Block{

    public BlockExposedCutCopper() {
        super( "minecraft:exposed_cut_copper" );
    }

    @Override
    public ItemExposedCutCopper toItem() {
        return new ItemExposedCutCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.EXPOSED_CUT_COPPER;
    }
}
