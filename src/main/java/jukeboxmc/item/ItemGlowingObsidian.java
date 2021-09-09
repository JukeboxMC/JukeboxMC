package jukeboxmc.item;

import org.jukeboxmc.block.BlockGlowingObsidian;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemGlowingObsidian extends Item {

    public ItemGlowingObsidian() {
        super ( "minecraft:glowingobsidian" );
    }

    @Override
    public BlockGlowingObsidian getBlock() {
        return new BlockGlowingObsidian();
    }
}
