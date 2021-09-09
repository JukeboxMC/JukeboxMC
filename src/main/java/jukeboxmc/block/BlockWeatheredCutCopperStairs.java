package jukeboxmc.block;

import org.jukeboxmc.item.ItemWeatheredCutCopperStairs;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredCutCopperStairs extends Block{
    
    public BlockWeatheredCutCopperStairs() {
        super( "minecraft:weathered_cut_copper_stairs" );
    }

    @Override
    public ItemWeatheredCutCopperStairs toItem() {
        return new ItemWeatheredCutCopperStairs();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEATHERED_CUT_COPPER_STAIRS;
    }
}
