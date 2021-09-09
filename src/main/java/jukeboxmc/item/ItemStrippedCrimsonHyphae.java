package jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedCrimsonHyphae;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedCrimsonHyphae extends Item {

    public ItemStrippedCrimsonHyphae() {
        super ( "minecraft:stripped_crimson_hyphae" );
    }

    @Override
    public BlockStrippedCrimsonHyphae getBlock() {
        return new BlockStrippedCrimsonHyphae();
    }
}
