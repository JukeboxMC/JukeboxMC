package org.jukeboxmc.item;

import org.jukeboxmc.block.BlockIronBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemIronBlock extends Item {

    public ItemIronBlock() {
        super ( "minecraft:iron_block" );
    }

    @Override
    public BlockIronBlock getBlock() {
        return new BlockIronBlock();
    }
}
