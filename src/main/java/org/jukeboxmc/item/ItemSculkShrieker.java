package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSculkShrieker;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSculkShrieker extends Item {

    //NOTE: Only in item and block palette but the item/block is not in the game yet
    public ItemSculkShrieker() {
        super( "minecraft:sculk_shrieker" );
    }

    @Override
    public BlockSculkShrieker getBlock() {
        return new BlockSculkShrieker();
    }
}
