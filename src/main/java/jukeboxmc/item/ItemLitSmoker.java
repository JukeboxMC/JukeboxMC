package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingSmoker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemLitSmoker extends Item {

    public ItemLitSmoker() {
        super ( "minecraft:lit_smoker" );
    }

    @Override
    public BlockGlowingSmoker getBlock() {
        return new BlockGlowingSmoker();
    }
}
