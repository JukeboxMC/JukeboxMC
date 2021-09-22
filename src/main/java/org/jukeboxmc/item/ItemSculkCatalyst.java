package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSculkCatalyst;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSculkCatalyst extends Item {

    //NOTE: Only in item and block palette but the item/block is not in the game yet
    public ItemSculkCatalyst() {
        super( "minecraft:sculk_catalyst" );
    }

    @Override
    public BlockSculkCatalyst getBlock() {
        return new BlockSculkCatalyst();
    }
}
