package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockSculk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemSculk extends Item {

    //NOTE: Only in item and block palette but the item/block is not in the game yet
    public ItemSculk() {
        super( "minecraft:sculk" );
    }

    @Override
    public BlockSculk getBlock() {
        return new BlockSculk();
    }
}
