package jukeboxmc.item;

import org.jukeboxmc.block.BlockWeatheredCutCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWeatheredCutCopper extends Item {

    public ItemWeatheredCutCopper() {
        super( "minecraft:weathered_cut_copper" );
    }

    @Override
    public BlockWeatheredCutCopper getBlock() {
        return new BlockWeatheredCutCopper();
    }
}
