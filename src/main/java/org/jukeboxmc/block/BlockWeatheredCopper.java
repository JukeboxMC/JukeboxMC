package org.jukeboxmc.block;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemWeatheredCopper;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWeatheredCopper extends Block {

    public BlockWeatheredCopper() {
        super( "minecraft:weathered_copper" );
    }

    @Override
    public ItemWeatheredCopper toItem() {
        return new ItemWeatheredCopper();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WEATHERED_COPPER;
    }
}
