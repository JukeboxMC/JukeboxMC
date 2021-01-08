package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockHoneyBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemHoneyBlock extends Item {

    public ItemHoneyBlock() {
        super( "minecraft:honey_block", -220 );
    }

    @Override
    public BlockHoneyBlock getBlock() {
        return new BlockHoneyBlock();
    }
}
