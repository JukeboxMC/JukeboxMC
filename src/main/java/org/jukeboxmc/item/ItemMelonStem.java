package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockMelonStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemMelonStem extends Item {

    public ItemMelonStem() {
        super( "minecraft:melon_stem", 105 );
    }

    @Override
    public BlockMelonStem getBlock() {
        return new BlockMelonStem();
    }
}
