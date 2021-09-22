package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSculkVein;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSculkVein extends Item {

    //NOTE: Only in item and block palette but the item/block is not in the game yet
    public ItemSculkVein() {
        super( "minecraft:sculk_vein" );
    }

    @Override
    public BlockSculkVein getBlock() {
        return new BlockSculkVein();
    }
}
