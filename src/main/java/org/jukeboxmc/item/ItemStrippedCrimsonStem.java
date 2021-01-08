package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockStrippedCrimsonStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemStrippedCrimsonStem extends Item {

    public ItemStrippedCrimsonStem() {
        super( "minecraft:stripped_crimson_stem", -240 );
    }

    @Override
    public BlockStrippedCrimsonStem getBlock() {
        return new BlockStrippedCrimsonStem();
    }
}
